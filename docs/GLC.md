// * Declaraciones Yacc
%token INT_CONSTANT
%token INPUT
%token PRINT
%token DEF
%token WHILE
%token IF
%token ELSE
%token INT
%token DOUBLE
%token CHAR
%token STRUCT
%token RETURN
%token VOID
%token ID
%token REAL_CONSTANT
%token CHAR_CONSTANT
%token GREATER
%token SMALLER
%token EQUALS
%token NEGATION
%token MAIN
%token OR
%token AND


%right '='
%left OR AND

%left EQUALS NEGATION SMALLER '<' GREATER '>'

%left '-' '+'
%left '*' '/' '%'

%nonassoc CAST
%right UNARIO
%nonassoc '!'
%left '.'
%nonassoc '[' ']'
%nonassoc '(' ')'

%nonassoc ':'
%nonassoc ELSE



%%
// * Gramática y acciones Yacc

programa : definiciones DEF MAIN '(' ')'':'VOID '{' body '}';	

definiciones: definiciones definicion 							
	 | /* empty */												
	 ;


definicion: def ';'												
			| funcion											
			;

// *********  FUNCIONES  *********

funcion: DEF ID '(' params ')' ':' retorno '{' body '}';     	

retorno: tipo 													
		| VOID 													
		; 											



body: defs														
	| sentencias												
	| defs sentencias											
	|															
	;


params:  /* empty */											
		| param													
		;

param: par														
	| param ',' par												

par:  ID ':' tipo;												


// *********  DEFINICIONES  *********

defs: def ';'													
	| defs def ';'												
	;
				
	
def: ids ':' tipo												


ids: ID															
	| ids ',' ID												
	;
								   
tipo: INT 														
	| DOUBLE 													
	| CHAR														
	|'['INT_CONSTANT']' tipo									
	| STRUCT '{' campos '}'										
	;


campos: campo													
		|campos campo 											
		;
		
campo: ids ':' tipo ';';										

// *********  SENTENCIAS  *********

sentencias: sentencia											
		| sentencias sentencia									
		;


sentencia: PRINT list ';'										
		| INPUT list ';'										
		| RETURN expresion ';'									
		| condicionalSimple										
		| condicionalComplejo									
		| while													
		| asignacion ';'										
		| invocacion ';'										
		;
	

expresion: ID 													
		| INT_CONSTANT											
		| CHAR_CONSTANT											
		| REAL_CONSTANT											
		| '(' expresion ')'										
		| expresion '[' expresion ']'							
		|  expresion '.' ID										
		| '(' tipo ')' expresion  %prec CAST					
		| '-' expresion %prec UNARIO							
		| '!' expresion											
		|  expresion '*' expresion	 							
		|  expresion '/' expresion	 							
		|  expresion '%' expresion	 							
		|  expresion '+' expresion	 							
		|  expresion '-' expresion	 							
		| expresion '>' expresion	 							
		| expresion GREATER expresion 							
		| expresion '<' expresion								
		| expresion SMALLER expresion							
		| expresion NEGATION expresion							
		| expresion EQUALS expresion							
		| expresion AND expresion								
		| expresion OR expresion								
		| ID '(' args ')'										
		;
		
		
list: expresion													
	| list ',' expresion										
	;
	
asignacion: expresion '=' expresion ;							

invocacion: ID '(' args ')'										


// *********  WHILE  *********

while: WHILE expresion ':' '{' sentencias '}' ;					

// *********  IF-ELSE  *********


condicionalSimple: IF expresion ':' cuerpo; 		
condicionalComplejo: IF expresion ':' cuerpo else;				

else: ELSE cuerpo ;												

cuerpo: sentencia												
		| '{' sentencias '}'   									
		;
		
				
// *********  INVOCACIÓN DE FUNCIONES  *********

args:  /* empty */												
		| arg													
		;

arg: expresion													
	| arg ',' expresion											
