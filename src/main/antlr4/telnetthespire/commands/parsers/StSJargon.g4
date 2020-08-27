grammar StSJargon;

order: command (command | implicit)* (NEWLINE | <EOF>);
command: commandName argument*;
implicit: ',' argument+;
commandName: '.' TEXTARG;
argument: (textArgument | floatArgument | naturalArgument);

textArgument: TEXTARG;
floatArgument: FLOAT;
naturalArgument: NATURAL;

fragment LETTER: [a-zA-Z_];

NATURAL: ( '0' | [1-9] DIGIT*) ;
FLOAT: NATURAL ('.' DIGIT+)?;
TEXTARG: (LETTER|DIGIT)+;
DIGIT: [0-9];
WHITESPACE : (' ' | '\t') -> skip;
NEWLINE:'\r'? '\n' ;
