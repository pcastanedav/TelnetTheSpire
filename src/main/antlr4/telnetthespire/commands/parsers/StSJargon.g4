grammar StSJargon;

order: command (command | implicit)* NEWLINE;
command: commandName argument*;
implicit: ',' argument+;
commandName: '.' WORD;
argument: (textArgument | floatArgument | naturalArgument);

floatArgument: FLOAT;
naturalArgument: NATURAL;
textArgument: (WORD|DIGIT)+;

fragment LETTER: [a-zA-Z];

WORD: LETTER+;
NATURAL: ( '0' | [1-9] DIGIT*);
FLOAT: NATURAL ('.' DIGIT+)?;
DIGIT: [0-9];
WHITESPACE : (' ' | '\t') -> skip;
NEWLINE:'\r'? '\n' ;
