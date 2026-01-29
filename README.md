• O nome do padrão de projeto.

• Uma breve justificativa para sua escolha.

• A identificação clara de cada classe do projeto e qual papel (participante) ela
assume no padrão (ex: Memento, Originator, Caretaker)

---

# Questão 1: Strategy Pattern

QUESTÃO 1:

    Identificação:

        A primeira questão do projeto envolve a resolução de um problema comportamental envolvendo a classe Autenticador, 
        a qual se responsabiliza de receber um documento e seu tipo para determinar qual o tratamento que esse documento 
        terá em sua geração de número de autenticação, afinal um documento criminal apresenta diferente estratégia de um 
        pessoal. 
        
        Entretanto, para a verificação do tratamento devido é realizado uma sequência de if - else interno, acoplando o 
        código e dificultando um crescimento na quantidade de diferentes estratégias que possam ser utilizadas pelos 
        documentos, pois seria necessário abrir a classe Autenticador sempre que desejasse alterar ou adicionar uma 
        estratégia. 

        EX:

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

        Uma maneira de resolver esse problema seria utilizando o padrão Strategy, pois através dele o 
        Autenticador não é mais o responsável por executar a operação de geração de código auteticador 
        nos documentos, 
        assim somente passa a responsabilidade para o Strategy fornecido pelo próprio cliente.

    Alteração:

        Originalmente, a classe abstrata AbstractGerenciadorDocumentosUI possuia uma lista String de 
        tipos de documentação que eram passados para a barra superior da aplicação, então o processo 
        de criação de um documento consistia no cliente selecionar o tipo desejado e nível de 
        privacidade, assim o índice dessa lista correspondente ao tipo do documento criado era 
        enviado ao Autenticador que realizava a sequência de if - else anterior para determinar o 
        tratamento desejado. 

        Agora, a classe abstrata possui uma lista de NameGeneratingStrategy, interface do padrão 
        Strategy com os métodos necessários deles, podendo ser composta de qualquer objeto que 
        implemente essa interface, assim contendo uma lista de todas as estratégias da aplicação 
        (implementação concreta). Dessa forma, são passados ao Autenticador o documento e sua 
        respectiva estratégia (escolhidos pelo cliente), que então chama 
        documento.setNumero(strategy.generateName(documento)). 
    
    Conclusão:

        Portanto, caso deseje-se criar novas estratégias, não mais é necessário abrir uma classe 
        já existente no sistema e configurar sua adição como antes, somente é necessário 
        implementar a interface NameGeneratingStrategy e ser adicionada na lista de tipos em 
        AbstractGerenciadorDocumentosUI.

        Cliente:

            public abstract class AbstractGerenciadorDocumentosUI

        Contexto:

            public class Autenticador

        Interface:

            public interface NameGeneratingStrategy

        Implementações concretas:

            public class ConfidencialNameGenerator
            public class CriminalNameGenerator
            public class ExportacaoNameGenerator
            public class PessoalNameGenerator

        Classe abstrata:

            AbstractNameGeneratingStrategy (para sobrescrever o método toString())



## --- EXPLICAR MOTIVAÇÃO E PARTICIPANTES

# Questão 2: Command Pattern + Composite Pattern

### --- EXPLICAR MOTIVAÇÃO E PARTICIPANTES

## IMPORTANTE: Explicar de forma clara que utilizamos um Lazy Receiver para corrigir cenários onde o estado dos objetos alvo do comando eram divergentes após a execução de macros.
