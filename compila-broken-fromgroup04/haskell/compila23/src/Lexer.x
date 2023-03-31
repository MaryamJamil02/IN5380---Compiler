{
module Lexer
  (
    Alex
  , AlexPosn (..)
  , alexGetInput
  , alexError
  , runAlex
  , alexMonadScan
  , mkRange
  , takeToken
  , takeStringToken
  , scanMany
  , runAlex'
  , scanMany'

  , Range (..)
  , RangedToken (..)
  , Token (..)
  ) where

import Data.ByteString.Lazy.Char8 (ByteString)
import Data.Char (toUpper)
import qualified Data.ByteString.Lazy.Char8 as BS
}

%wrapper "monadUserState-bytestring"

$digit        = [0-9]
$nonZeroDigit = [1-9]
$alpha        = [a-zA-Z]
$singleQuote  = \'
$doubleQuote  = \"
$underscore   = _


@null         = null 
@boolean      = true | false
@float        = $digit+ . $digit+
@integer      = $digit+
@string       = ( $singleQuote [^$singleQuote]* $singleQuote) 
              | ( $doubleQuote [^$doubleQuote]* $doubleQuote)
@name         = $alpha+ ($alpha | $digit | underscore)* ($alpha | $digit)*

tokens :-

-- Whitespace
<0> $white+ ;

-- Keywords
<0> begin             { takeToken Begin         }
<0> end               { takeToken End           }
<0> program           { takeToken Program       }
<0> procedure         { takeToken Procedure     }
<0> var               { takeToken Variable      }
<0> if                { takeToken If            }
<0> then              { takeToken Then          }
<0> else              { takeToken Else          }
<0> fi                { takeToken Fi            }
<0> in                { takeToken In            }
<0> while             { takeToken While         }
<0> do                { takeToken Do            }
<0> od                { takeToken Od            }
<0> return            { takeToken Return        }
<0> new               { takeToken New           }
<0> ref               { takeToken Ref           }
<0> deref             { takeToken Deref         }
-- <0> type              { takeToken Type          }

-- Logical operators
<0> not               { takeToken Not           }
<0> "="               { takeToken Eq            }
<0> "<>"              { takeToken Neq           }
<0> "&&"              { takeToken And           }
<0> "||"              { takeToken Or            }

-- Comparison operators
<0> "<"               { takeToken Lt            }
<0> "<="              { takeToken Lte           }
<0> ">"               { takeToken Gt            }
<0> ">="              { takeToken Gte           }

-- Arithmentic operators
<0> "+"               { takeToken Addition      }
<0> "-"               { takeToken Subtraction   }
<0> "*"               { takeToken Multiplication}
<0> "/"               { takeToken Division      }
<0> "^"               { takeToken Exponentiation}

-- Other symbols
<0> "("               { takeToken LParen        }
<0> ")"               { takeToken RParen        }
<0> "["               { takeToken LBracket      }
<0> "]"               { takeToken RBracket      }
<0> "{"               { takeToken LCurlyBracket }
<0> "}"               { takeToken RCurlyBracket }
<0> ","               { takeToken Comma         }
<0> ":"               { takeToken Colon         }
<0> ";"               { takeToken Semicolon     }
<0> ":="              { takeToken Declaration   }
<0> "."               { takeToken Dot           }

-- Literals
<0> @null             { takeToken Null          }
<0> @boolean          { takeBooleanToken        }
<0> @integer          { takeIntegerToken        }
<0> @float            { takeFloatToken          }
<0> @string           { takeStringToken         }
<0> @name             { takeNameToken           }

-- og comments!

{

data AlexUserState = AlexUserState
  {
  }

alexInitUserState :: AlexUserState
alexInitUserState = AlexUserState

alexEOF :: Alex RangedToken
alexEOF = do
  (pos, _, _, _) <- alexGetInput
  pure $ RangedToken EOF (Range pos pos)

data Range = Range
  { start :: AlexPosn
  , stop :: AlexPosn
  } deriving (Eq, Show)

data RangedToken = RangedToken
  { rtToken :: Token
  , rtRange :: Range
  } deriving (Eq, Show)

data Token = 
  -- Keywords
    Begin
  | End
  | Program
  | Procedure
  | Variable
  | If
  | Then
  | Else
  | Fi
  | In
  | While
  | Do
  | Od
  | Return
  | New
  | Ref
  | Deref
--   | Type
  -- Types
  -- | StringType
  -- | IntType
  -- | FloatType
  -- | BoolType

  -- Logical operators
  | Not
  | And
  | Or

  -- Comparisons
  | Eq
  | Neq
  | Lt
  | Lte
  | Gt
  | Gte

  -- Arithmetic operators
  | Addition
  | Subtraction
  | Multiplication
  | Division
  | Exponentiation

  -- Symbols
  | LParen
  | RParen
  | LBracket
  | RBracket
  | LCurlyBracket
  | RCurlyBracket
  | Comma
  | Colon
  | Semicolon
  | Declaration
  | Dot

  -- Literals
  | Null
  | Boolean Bool
  | Integer Integer
  | Float   Float
  | String  ByteString
  | Name    ByteString

  -- EOF
  | EOF

  deriving (Eq, Show)

--- Tokenize functions ---
takeToken :: Token -> AlexAction RangedToken
takeToken ctor inp len =
  pure RangedToken
    { rtToken = ctor
    , rtRange = mkRange inp len
    }

takeBooleanToken :: AlexAction RangedToken
takeBooleanToken inp@(_, _, str, _) len =
  pure RangedToken
    { rtToken = Boolean $ stringToBool $ BS.unpack $ BS.take len str
    , rtRange = mkRange inp len
    }

takeIntegerToken :: AlexAction RangedToken
takeIntegerToken inp@(_, _, str, _) len =
  pure RangedToken
    { rtToken = Integer $ read $ BS.unpack $ BS.take len str
    , rtRange = mkRange inp len
    }

takeFloatToken :: AlexAction RangedToken
takeFloatToken inp@(_, _, str, _) len =
  pure RangedToken
    { rtToken = Float $ read $ BS.unpack $ BS.take len str
    , rtRange = mkRange inp len
    }

takeStringToken :: AlexAction RangedToken
takeStringToken inp@(_, _, str, _) len =
  pure RangedToken
    { rtToken = String $ BS.take len str
    , rtRange = mkRange inp len
    }

takeNameToken :: AlexAction RangedToken
takeNameToken inp@(_, _, str, _) len =
  pure RangedToken
    { rtToken = Name $ BS.take len str
    , rtRange = mkRange inp len
    }

--- Helper functions ---
mkRange :: AlexInput -> Int64 -> Range
mkRange (start, _, str, _) len = Range{start = start, stop = stop}
  where
    stop = BS.foldl' alexMove start $ BS.take len str

{-- 
  Capitalizing the string so that it could be interpred as a Bool.
  When reading a string "True"/"False", it could be read as a string,
  so the type annotation makes it clear that its a Bool. 
--}
stringToBool :: String -> Bool
stringToBool = read . capitalize

capitalize :: String -> String
capitalize [] = []
capitalize (x:xs) = toUpper x : xs

scanMany :: ByteString -> Either String [RangedToken]
scanMany input = runAlex input go
  where
    go = do
      output <- alexMonadScan
      if rtToken output == EOF
        then pure [output]
        else (output :) <$> go

{--
  The suggestion of using runAlex on Strings produces a type error.
  Fixed by combining runAlex with converter from String to Bytestring.
--}
runAlex'  = runAlex  . BS.pack
scanMany' = scanMany . BS.pack

}