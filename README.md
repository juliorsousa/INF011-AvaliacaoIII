• O nome do padrão de projeto.

• Uma breve justificativa para sua escolha.

• A identificação clara de cada classe do projeto e qual papel (participante) ela
assume no padrão (ex: Memento, Originator, Caretaker)

---

# Questão 1:

## Padrão: Strategy Pattern

### Identificação:

A primeira questão do projeto envolve a resolução de um problema comportamental envolvendo a classe Autenticador, 
a qual se responsabiliza de receber um documento e seu tipo para determinar qual o tratamento que esse documento 
terá em sua geração de número de autenticação, afinal um documento criminal apresenta diferente estratégia de um 
pessoal. 

Entretanto, para a verificação do tratamento devido é realizado uma sequência de if - else interno, acoplando o 
código e dificultando um crescimento na quantidade de diferentes estratégias que possam ser utilizadas pelos 
documentos, pois seria necessário abrir a classe Autenticador sempre que desejasse alterar ou adicionar uma 
estratégia. 

### Exemplo:

```java
if(tipo == 0)
        numero = "CRI-" + LocalDate.now().getYear() + "-" + documento.hashCode();
    else if(tipo == 1)
        numero = "PES-" + LocalDate.now().getDayOfYear() + "-" + documento.getProprietario().hashCode();
    else if (tipo == 2) {
        if (documento.getPrivacidade() == Privacidade.SIGILOSO) {
            numero = "SECURE-" + documento.getNumero().hashCode();
        } else {
            numero = "PUB-" + documento.hashCode();
        }
    }else
        numero = "DOC-" + System.currentTimeMillis(); 
    documento.setNumero(numero);
```

Uma maneira de resolver esse problema seria utilizando o padrão Strategy, pois através dele o 
Autenticador não é mais o responsável por executar a operação de geração de código auteticador 
nos documentos, 
assim somente passa a responsabilidade para o Strategy fornecido pelo próprio cliente.

### Alteração:

Originalmente, a classe abstrata AbstractGerenciadorDocumentosUI possuia uma lista String de 
tipos de documentação que eram passados para a barra superior da aplicação, então o processo 
de criação de um documento consistia no cliente selecionar o tipo desejado e nível de 
privacidade, assim o índice dessa lista correspondente ao tipo do documento criado era 
enviado ao Autenticador que realizava a sequência de if - else anterior para determinar o 
tratamento desejado. 

Agora, a classe abstrata possui uma lista de NameGenerator, interface do padrão 
Strategy com os métodos necessários deles, podendo ser composta de qualquer objeto que 
implemente essa interface, assim contendo uma lista de todas as estratégias da aplicação 
(implementação concreta). Dessa forma, são passados ao Autenticador o documento e sua 
respectiva estratégia (escolhidos pelo cliente), que então chama 
documento.setNumero(strategy.generateName(documento)). 

### Conclusão:

Portanto, caso deseje-se criar novas estratégias, não mais é necessário abrir uma classe 
já existente no sistema e configurar sua adição como antes, somente é necessário 
implementar a interface NameGenerator e ser adicionada na lista de tipos em 
AbstractGerenciadorDocumentosUI.

### Participantes: 

Cliente: `AbstractGerenciadorDocumentosUI`

Contexto: `Autenticador`

Interface: `NameGenerator`

Implementações concretas:

 - `ConfidencialNameGenerator`
 - `CriminalNameGenerator`
 - `ExportacaoNameGenerator`
 - `PessoalNameGenerator`

Classe abstrata: `AbstractNameGeneratingStrategy (para sobrescrever o método toString())`

# Questão 2:

## Padrão: Command Pattern + Composite Pattern

### Identificação:

A segunda questão apresenta não um, mas algumas problemáticas que devem ser resolvidas para o 
melhor funcionamento do sistema, como a criação e armazenamento de um log de todas as operações 
realizadas sobre os documentos, suporte às operações compostas (comandos macros), possibilidade 
de desfazer e refazer operações e maior segurança ao evitar que as diversas interfaces do 
sistema chamem métodos como o de assinar() diretamente.

Um possível padrão a ser aplicado seria o Command, que consiste em encapsular uma solicitação 
(operação) em um objeto, permitindo a paremetrização dos clientes para diferentes requisições, 
registro de log, operações de undo (desfazer) e redo (refazer), além do desacoplamento das classes 
de UI com a lógica de negóçios do sistema, assim elas sempre chamam a classe Invoker 
(CommandContext) que se responsabiliza em executar o command. 

Portanto as requisições da questão poderiam ser supridas com esse padrão.

### Participantes

Command (Interface/Abstract): Definem o contrato básico (execute, undo, redo).

 - `Command`
 - `AbstractDocumentCommand`

Concrete Command: Eles sabem como realizar uma ação específica no documento.

 - `AssinarDocumentoCommand`
 - `SalvarDocumentoCommand`
 - `ProtegerDocumentoCommand`
 - `TornarUrgenteCommand`

Macro Command (Concrete Command): Eles são a composição de dois ou mais comandos em um, 
apresentando estrutura similar à pertencente ao padrão composite. Nesse caso, as folhas 
seriam os comandos concretos como: AssinarDocumentoCommand, já os composites seriam os 
comandos macro que podem ser compostos de outro(s) comando(s) macro e/ou uma ou mais folhas.
Para o sistema foram criados dois comandos macro:

 - `SalvarEAssinarMacro`: Encapsula os comandos de salvar e assinar; 
 - `PriorizarMacro`: Encapsula os comandos de tornar urgente e assinar.

Receiver: É quem realmente possui a lógica de negócio e o estado dos documentos.

 - `GerenciadorDocumentoModel`

Invoker: Ele recebe o comando, executa-o e o armazena nas pilhas de desfazer/refazer.

 - `CommandContext`

Client: É quem decide qual comando deve ser criado e quando.

 - `MyGerenciadorDocumentoUI`

 ### Fluxo de operação

Para o fluxo podemos usar como exemplo o comando macro de salvar e assinar e as classes 
participantes do padrão apresentadas no tópico anterior.

Quando o usuário seleciona a operação de salvar e assinar a classe Client chama o método 
salvarEAssinar(Documento doc, String conteudo) da classe Receiver, que por sua vez cria o 
respectivo objeto command (nesse exemplo o SalvarEAssinarMacro), pois possue a lógica de 
negócio. Em seguida, adiciona o comando na classe Invoker reponsável por invocar o método 
execute() do comando e armazená-lo numa pilha de execução com o intuito de poder desfazer 
ou refazer a operação dinâmicamente. 

No método execute, o comando realiza as operações nele programadas, nesse exemplo são criados 
internamente no SalvarEAssinarMacro um SalvarDocumentoCommand e um SalvarDocumentoCommand que 
têm seus métodos execute() também chamados.

### Detalhes

Para o bom funcionamento dos macro comandos foi utilizado um Lazy Receiver para corrigir 
cenários onde o estado dos objetos alvo dos comandos eram divergentes após a execução dos mesmos.

Um desafio notado ao implementar o padrão Composite em comandos macros foi o de garantir que todos 
os subcomandos operassem sobre a versão mais recente do documento em questão, afinal se o comandos internos 
do macro recebem o documento em sua criação por instanciação hà o risco de operarem sobre uma versão
obsoleta desse documento.

Para resolver isso, utilizamos o Lazy Receiver, assim os comandos não guardam uma referência estática ao 
documento. Em vez disso, através da classe AbstractDocumentCommand, eles invocam o método getDocumentoAtual() 
do GerenciadorDocumentoModel (o Receiver principal) apenas no momento da execução (execute()), garantindo a 
integridade do estado do documento em operações encadeadas, como em "Salvar e Assinar".

Padrão Composite nos macros:

 - `Macro`: Classe Composite
 - `Command`: Interface Component
 - `AssinarDocumentoCommand`: Classe leaf

Assim, as classes que extendem Macro são composites e aquelas que extendem AssinarDocumentoCommand são leafs.

