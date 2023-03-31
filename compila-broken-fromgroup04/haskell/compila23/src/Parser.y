{
{-# LANGUAGE DeriveFoldable #-}
module Parser
  ( parseCompila23
  ) where

import Data.ByteString.Lazy.Char8 (ByteString)
import Data.Maybe (fromJust)
import Data.Monoid (First (..))

import qualified Lexer as L
}

%name parseCompila23 decs
%tokentype { L.RangedToken }
%error     { parseError }
%monad     { L.Alex } { >>= } { pure }
%lexer     { lexer } { L.RangedToken L.EOF _ }

%token -- make use of double and single quotes more consistent!!
  -- Keywords
  begin      { L.RangedToken L.Begin _          }
  end        { L.RangedToken L.End _            }
  program    { L.RangedToken L.Program _        }
  procedure  { L.RangedToken L.Procedure _      }
  var        { L.RangedToken L.Variable _       }
  if         { L.RangedToken L.If _             }
  then       { L.RangedToken L.Then _           }
  else       { L.RangedToken L.Else _           }
  fi         { L.RangedToken L.Fi _             }
  in         { L.RangedToken L.In _             }
  while      { L.RangedToken L.While _          }
  do         { L.RangedToken L.Do _             }
  od         { L.RangedToken L.Od _             }
  return     { L.RangedToken L.Return _         }
  new        { L.RangedToken L.New _            }
  ref        { L.RangedToken L.Ref _            }
  deref      { L.RangedToken L.Deref _          }
--   type       { L.RangedToken L.Type _           }
  -- add actual type-types? e.g. stringType etc.

  -- Logical operators
  not        { L.RangedToken L.Not _            }
  "="        { L.RangedToken L.Eq _             }
  "<>"       { L.RangedToken L.Neq _            }
  "&&"       { L.RangedToken L.And _            }
  "||"       { L.RangedToken L.Or _             }

  -- Comparison operators
  "<"        { L.RangedToken L.Lt _             }
  "<="       { L.RangedToken L.Lte _            }
  ">"        { L.RangedToken L.Gt _             }
  ">="       { L.RangedToken L.Gte _            }

  -- Arithmetic operators
  '+'        { L.RangedToken L.Addition _       }
  '-'        { L.RangedToken L.Subtraction _    }
  '*'        { L.RangedToken L.Multiplication _ }
  '/'        { L.RangedToken L.Division _       }
  "^"        { L.RangedToken L.Exponentiation _ }

  -- Other symbols
  '('        { L.RangedToken L.LParen _         }
  ')'        { L.RangedToken L.RParen _         }
  '['        { L.RangedToken L.LBracket _       }
  ']'        { L.RangedToken L.RBracket _       }
  "{"        { L.RangedToken L.LCurlyBracket _  }
  '}'        { L.RangedToken L.RCurlyBracket _  }
  ','        { L.RangedToken L.Comma _          }
  ':'        { L.RangedToken L.Colon _          }
  ';'        { L.RangedToken L.Semicolon _      }
  ":="       { L.RangedToken L.Declaration _    }
  "."        { L.RangedToken L.Dot _            }

  -- Literals
  null       { L.RangedToken L.Null _           }
  boolean    { L.RangedToken (L.Boolean _) _    }
  integer    { L.RangedToken (L.Integer _) _    }
  float      { L.RangedToken (L.Float _) _      }
  string     { L.RangedToken (L.String _) _     }
  name       { L.RangedToken (L.Name _) _       }

%%

-- we wrap these in L.Range-objects
identifier :: { Name L.Range }
  : name { unTok $1 (\range (L.Name identifier) -> Name range identifier) }

exp :: { Exp L.Range }
  : integer     { unTok $1 (\range (L.Integer int) -> EInt range int) }
  | identifier  { EVar (info $1) $1 }
  | string      { unTok $1 (\range (L.String string) -> EString range string) }

-- called type not to be conflicted with type from above
-- figure out how to use type token. must probably wrap like name
-- < start
type :: { Type L.Range }
  : identifier     { TVar (info $1) $1 }
  | '(' ')'        { TUnit (L.rtRange $1 <-> L.rtRange $2) }
  | '(' type ')'  { TPar (L.rtRange $1 <-> L.rtRange $3) $2 }
  | '[' type ']'  { TList (L.rtRange $1 <-> L.rtRange $3) $2 }

typeAnnotation :: { Type L.Range }
  : ':' type { $2 }
-- end >

argument :: { Argument L.Range }
  : '(' identifier optional(typeAnnotation) ')' { Argument (L.rtRange $1 <-> L.rtRange $4) $2 $3 }
  | identifier                                  { Argument (info $1) $1 Nothing }

dec :: { Dec L.Range }
  : var identifier many(argument) optional(typeAnnotation) ":=" exp { Dec (L.rtRange $1 <-> info $6) $2 $3 $4 $6 }

decs :: { [Dec L.Range] }
  : many(dec) { $1 }

-- Utility to avoid so much repetition
optional(p)
  :   { Nothing }
  | p { Just $1 }

many_rev(p)
  : {- empty list -} { [] }
  | many_rev(p) p    { $2 : $1 }

many(p)
  : many_rev(p) { reverse $1 }



{
parseError :: L.RangedToken -> L.Alex a
parseError _ = do
  (L.AlexPn _ line column, _, _, _) <- L.alexGetInput
  L.alexError $ "Parse error at line " <> show line <> ", column " <> show column

lexer :: (L.RangedToken -> L.Alex a) -> L.Alex a
lexer = (=<< L.alexMonadScan)

-- * AST
data Name a
  = Name a ByteString
  deriving (Foldable, Show)

data Type a
  = TVar a (Name a)
  | TPar a (Type a) -- kinda reduntant since we can figure out precedence of operators in the AST
  | TUnit a
  | TList a (Type a)
  | TArrow a (Type a) (Type a)
  deriving (Foldable, Show)

data Argument a
  = Argument a (Name a) (Maybe (Type a))
  deriving (Foldable, Show)

data Dec a
  = Dec a (Name a) [Argument a] (Maybe (Type a)) (Exp a)
  deriving (Foldable, Show)

data Operator a
  = Addition a
  | Subtraction a
  | Multiplication a
  | Division a
  | Eq a
  | Neq a
  | Lt a
  | Lte a
  | Gt a
  | Gte a
  | And a
  | Or a
  | Exponentiation a
  deriving (Foldable, Show)

data Exp a
  = EInt a Integer
  | EVar a (Name a)
  | EString a ByteString
  | EUnit a
  | EList a [Exp a]
  | EPar a (Exp a) -- can in theory be removed
  | EApp a (Exp a) (Exp a)
  | EIfThenFi a (Exp a) (Exp a)
  | EIfThenElseFi a (Exp a) (Exp a) (Exp a)
  | ENeg a (Exp a)
  | EBinOp a (Exp a) (Operator a) (Exp a)
  | EOp a (Dec a) (Exp a)
  deriving (Foldable, Show)

-- {- Auxiliary functions -}
-- | Build a simple node by extracting its token type and range.
unTok :: L.RangedToken -> (L.Range -> L.Token -> a) -> a
unTok (L.RangedToken tok range) ctor = ctor range tok

-- | Unsafely extracts the the metainformation field of a node.
info :: Foldable f => f a -> a
info = fromJust . getFirst . foldMap pure

-- | Performs the union of two ranges by creating a new range starting at the
-- start position of the first range, and stopping at the stop position of the
-- second range.
-- Invariant: The LHS range starts before the RHS range.
(<->) :: L.Range -> L.Range -> L.Range
L.Range a1 _ <-> L.Range _ b2 = L.Range a1 b2
}