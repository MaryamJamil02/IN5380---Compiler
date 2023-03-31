# Grammar

EXP         -> OR-TEST                                  \
OR-TEST     -> OR-TEST  '||'    AND-TEST    | AND-TEST  \
AND-TEST    -> AND-TEST '&&'    OPERAND     | OPERAND   \
OPERAND     ->          'not'   OPERAND     | TERM      \
TERM        -> TERM     REL-OP  FACTOR      | FACTOR    \
FACTOR      -> FACTOR   ADD-OP  BASE        | BASE      \
BASE        -> BASE     MUL-OP  EXPO-EXP    | EXPO-EXP  \
EXPO-EXP    -> DOT-EXP  '^'     EXPO-EXP    | DOT-EXP   \
DOT-EXP     -> DOT-EXP  '.'     NAME        | epsilon   
