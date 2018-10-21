package commonStructures;

import entities.states.ChefState;
import entities.states.StudentState;
import entities.states.WaiterState;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.Field;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Classe que representa a troca de mensagens.
 * Segue o Builder Creational Pattern.
 */
public final class Message implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Enumerado com todas as mensagens possíveis.
     */
    public enum Type {
        /**
         * Chef aguarda que lhe seja atribuida uma tarefa.
         */
        WATCH_NEWS(1),
        /**
         * O Waiter atribui uma função ao Chef.
         */
        HAND_NOTE_TO_CHEF(2),
        /**
         * O Chef inicia o empratamento das porções para entregar ao Waiter.
         */
        START_PREPARATION(3),
        /**
         * O Chef prepara o empratamento das porções que entrega ao Waiter.
         */
        PROCEED_TO_PRESENTATION(4),
        /**
         * O Chef alerta o Waiter que pode recolher as porções.
         */
        KITCHEN_ALERT_WAITER(5),
        /**
         * O Waiter recolhe a porção.
         */
        COLLECT_PORTION(6),
        /**
         * O Chef verifica se foram entregues todas as porções.
         */
        ALL_PORTIONS_DELIVERED(7),
        /**
         * Resposta da verificcação se todas as porções foram entregues.
         */
        ALL_PORTIONS_DELIVERED_RESPONSE(8),
        /**
         * O Waiter informa o chef que todos os estudantes foram servidos.
         */
        KITCHEN_ALL_STUDENTS_SERVED(9),
        /**
         * O Chef prepara a porção seguinte.
         */
        HAVE_NEXT_PORTION_READY(10),
        /**
         * O Chef continua a preparação.
         */
        CONTINUE_PREPARATION(11),
        /**
         * Verificação se o pedido inteiro foi efetuado.
         */
        ORDER_COMPLETED(12),
        /**
         * Resposta à verificação de pedido.
         */
        ORDER_COMPLETED_RESPONSE(13),
        /**
         * O Chef termina as suas tarefas e limpa a cozinha.
         */
        CLEAN_UP(14),

        /**
         * Os estudantes entram no restaurante.
         */
        TABLE_ENTER(15),

        /**
         * Resposta à entrada dos estudantes no restaurante.
         */
        TABLE_ENTER_RESPONSE(16),

        /**
         * O Waiter recebe os estudantes.
         */
        SALUTE_CLIENT(17),

        /**
         * O Estudante lê o menu.
         */
        READ_MENU(18),
        /**
         * Os estudantes organizam o pedido caso já tenham escolhido.
         */
        EVERYBODY_CHOSEN(19),
        /**
         * Resposta ao à organização de pedidos.
         */
        EVERYBODY_CHOSEN_RESPONSE(20),

        /**
         * Os estudantes iniciam a preparação do pedido.
         */
        PREPARE_ORDER(21),

        /**
         * Os estudantes descrevem o pedido ao Waiter.
         */
        DESCRIBE_ORDER(22),

        /**
         * O Waiter recolhe o pedido.
         */
        GET_THE_PAD(23),

        /**
         * O estudante após realizar o seu pedido junta-se à conversa com os seus colegas.
         */
        JOIN_TALK(24),

        /**
         * O estudante informa o colega do seu pedido.
         */
        INFORM_COMPANION(25),

        /**
         * O Waiter entrega a porção ao estudante.
         */
        DELIVER_PORTION(26),

        /**
         * Verificação se o estudante já foi servido.
         */
        TABLE_ALL_STUDENTS_SERVED(27),
        /**
         * Resposta à verificação se o estudante já foi servido.
         */
        TABLE_ALL_STUDENTS_SERVED_RESPONSE(28),

        /**
         * O estudante começa a comer.
         */
        START_EATING(29),
        /**
         * O estudante termina de comer.
         */
        END_EATING(30),
        /**
         * Resposta à finalização da refeição.
         */
        END_EATING_RESPONSE(31),

        /**
         * Verificação se todos terminaram de comer.
         */
        EVERYBODY_FINISHED(32),
        /**
         * Reposta à verificação se todos terminaram de comer.
         */
        EVERYBODY_FINISHED_RESPONSE(33),
        /**
         * Sinalização do Waiter por parte do estudante.
         */
        SIGNAL_WAITER(34),

        /**
         * o último estudante a chegar paga a conta.
         */
        TABLE_SHOULD_ARRIVE_EARLIER(35),

        /**
         * O Waiter apresenta a conta ao estudante.
         */
        PRESENT_BILL(36),

        /**
         * O Estudante paga a conta.
         */
        HONOUR_BILL(37),
        /**
         * Quando paga a conta todos os estudantes saem do restaurante.
         */
        TABLE_EXIT(38),

        /**
         * Waiter aguarda por uma tarefa.
         */
        LOOK_AROUND(39),

        /**
         * Resposta à espera por tarefas.
         */
        LOOK_AROUND_RESPONSE(40),

        /**
         * Alteração do estado do Waiter para Appraising The Situation.
         */
        RETURN_TO_BAR(41),

        /**
         * O Waiter é chamado pelos estudantes.
         */
        BAR_ENTER(42),

        /**
         * Resposta à chamada do Waiter.
         */
        BAR_ENTER_RESPONSE(43),

        /**
         * O primeiro estudante a chegar chama o Waiter.
         */
        CALL_WAITER(44),

        /**
         * O Chef alerta o Waiter para recolher as porções.
         */
        BAR_ALERT_WAITER(45),

        /**
         * O Waiter prepara a conta.
         */
        PREPARE_BILL(46),

        /**
         * Chamado pelo o último estudante a chegar.
         */
        BAR_SHOULD_ARRIVE_EARLIER(47),

        /**
         * O Waiter despede-se do estudante.
         */
        SAY_GOODBYE(48),
        /**
         * Os estudantes abandonam o restaurante.
         */
        BAR_EXIT(49),

        /**
         * Atualização do prato.
         */
        UPDATE_COURSE(50),

        /**
         * Atualização do estado do Chef.
         */
        UPDATE_CHEF(51),

        /**
         * Atualização do estado do Waiter.
         */
        UPDATE_WAITER(52),
        /**
         * Atualização do estado do Student.
         */
        UPDATE_STUDENT(53),
        /**
         * Mensagem geral de tarefa adquirida.
         */
        MESSAGE_RECEIVED(54),
        /**
         * Mensagem geral de receção de informação por parte do logger.
         */
        LOGGER_MESSAGE_RECEIVED(55),
        /**
         * Mensagem enviada quando o MainBar termina.
         */
        BAR_FINISHED(56),
        /**
         * Mensagem enviada quando o MainKitchen termina.
         */
        KITCHEN_FINISHED(57),
        /**
         * Mensagem enviada quando o MainTable termina.
         */
        TABLE_FINISHED(58),
        /**
         * Mensagem enviada pelo Waiter quando termina o seu serviço.
         */
        WAITER_FINISHED(59);
        /**
         * ID associado ao tipo.
         */
        private final int id;

        /**
         * Criação da contante de acordo com o id dado.
         *
         * @param id associado ao valor do enumerado.
         */
        Type(int id) {
            this.id = id;
        }

        /**
         * Obtenção do ID.
         *
         * @return id do tipo.
         */
        public int getId() {
            return id;
        }
    }

    /**
     * Tipo da mensagem enviada.
     */
    private Type type;
    /**
     * Id necessário para fazer o update do estado do estudante no logger.
     */
    private long studentId;
    /**
     * Estado do estudante atualizado no Logger.
     */
    private StudentState studentState;
    /**
     * Estado do waiter atualizado no Logger.
     */
    private WaiterState waiterState;
    /**
     * Estado do Chef atualizado no logger.
     */
    private ChefState chefState;

    /**
     * Número do prato para atualizar no Logger.
     */
    private int numberCourse;
    /**
     * Controlo das operações no bar.
     */
    private char task;
    /**
     * Controlo da ordem de chegada na table.
     */
    private int arrivalOrder;
    /**
     * Controlo da escolha de todos os pedidos na table.
     */
    private boolean everybodyChosen;
    /**
     * Controlo de todos os estudantes que foram servidos na table.
     */
    private boolean tableAllStudentsServed;
    /**
     * Controlo de todos os estudantes que terminaram de comer.
     */
    private boolean endEating;
    /**
     * Controlo da verificação se todos os estudantes acabaram de comer.
     */
    private boolean everybodyFinished;
    /**
     * Controlo do termino das tarefas na cozinha.
     */
    private boolean kitchenOrderCompleted;
    /**
     * Controlo se todas as porções foram entregues na cozinha.
     */
    private boolean kitchenAllPortionsDelivered;

    /**
     * Criação do objeto mensagem por via do tipo fornecido.
     *
     * @param tipo da mensagem enviada.
     */
    public Message(Type type) {
        this.type = type;
        this.studentId = -1L;
        studentState = null;
        waiterState = null;
        chefState = null;

        numberCourse = -1;
        task = ' ';
        arrivalOrder = -1;
        everybodyChosen = false;
        tableAllStudentsServed = false;
        endEating = false;
        everybodyFinished = false;
        kitchenOrderCompleted = false;
        kitchenAllPortionsDelivered = false;
    }
    
    public Message (String stringXML)
    {
       InputSource in = new InputSource (new StringReader (stringXML));
       SAXParserFactory spf;
       SAXParser saxParser = null;

       spf = SAXParserFactory.newInstance ();
       spf.setNamespaceAware (false);
       spf.setValidating (false);
       try
       { saxParser = spf.newSAXParser ();
         saxParser.parse (in, new HandlerXML ());
       }
       catch (ParserConfigurationException e)
       { System.out.println ("Erro na instanciação do parser (configuração): " + e.getMessage () + "!");
         System.exit (1);
       }
       catch (SAXException e)
       { System.out.println ("Erro na instanciação do parser (SAX): " + e.getMessage () + "!");
         System.exit (1);
       }
       catch (IOException e)
       { System.out.println ("Erro na execução do parser (SAX): " + e.getMessage () + "!");
         System.exit (1);
       }
    }
    
    /**
     *  Este tipo de dados define o handler que gere os acontecimentos que ocorrem durante o parsing de um string XML.
     *
     */

     private class HandlerXML extends DefaultHandler
     {

       /**
        *  Parsing do string XML em curso
        *    @serialField parsing
        */

        private boolean parsing;

       /**
        *  Parsing de um elemento em curso
        *    @serialField element
        */

        private boolean element;

       /**
        *  Nome do elemento em processamento
        *    @serialField elemName
        */

        private String elemName;

       /**
        *  Início do processamento do string XML.
        *
        */

        @Override
        public void startDocument () throws SAXException
        {
        	type = null;
            studentId = -1L;
            studentState = null;
            waiterState = null;
            chefState = null;

            numberCourse = -1;
            task = ' ';
            arrivalOrder = -1;
            everybodyChosen = false;
            tableAllStudentsServed = false;
            endEating = false;
            everybodyFinished = false;
            kitchenOrderCompleted = false;
            kitchenAllPortionsDelivered = false;
            parsing = true;
        }


       /**
        *  Fim do processamento do string XML.
        *
        */

        @Override
        public void endDocument () throws SAXException
        {
           parsing = false;
        }

       /**
        *  Início do processamento de um elemento do string XML.
        *
        */

        @Override
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes atts) throws SAXException
        {
           element = parsing;
           if (parsing) elemName = qName;
        }

       /**
        *  Fim do processamento de um elemento do string XML.
        *
        */

        @Override
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException
        {
           element = false;
           elemName = null;
        }

       /**
        *  Processamento do conteúdo de um elemento do string XML.
        *
        */

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException
        {
           String elem;

           elem = new String (ch, start, length);
           if (parsing && element)
           {
        	   switch (elemName) {
               case "Tipo":
                   type = Type.valueOf(elem);
                   break;
               case "studentId":
                   studentId = Integer.valueOf(elem);
                   break;
               case "numberCourse":
                   numberCourse = Integer.valueOf(elem);
                   break;
               case "studentState":
                   studentState = StudentState.getStudentStateById(elem);
                   break;
               case "waiterState":
                   waiterState = WaiterState.getWaiterStateById(elem);
                   break;
               case "chefState":

                   chefState = ChefState.getChefStateById(elem);
                   break;
               case "kitchenAllPortionsDelivered":
                   kitchenAllPortionsDelivered = Boolean.valueOf(elem);
                   break;
               case "kitchenOrderCompleted":
                   kitchenOrderCompleted = Boolean.valueOf(elem);
                   break;
               case "task":
                   task = elem.charAt(0);
                   break;
               case "arrivalOrder":
                   arrivalOrder = Integer.valueOf(elem);
                   break;
               case "everybodyChosen":
                   everybodyChosen = Boolean.valueOf(elem);
                   break;
               case "tableAllStudentsServed":
                   tableAllStudentsServed = Boolean.valueOf(elem);
                   break;
               case "endEating":
                   endEating = Boolean.valueOf(elem);
                   break;
               case "everybodyFinished":
                   everybodyFinished = Boolean.valueOf(elem);
                   break;
           }
              }
        }
     }
    
    /**
     * Retorno do tipo da mensagem.
     *
     * @return tipo da mensagem.
     */
    public Type getType() {
        return type;
    }

    /**
     * Retorno do ID do estudante.
     *
     * @return ID do estudante.
     */
    public long getStudentId() {
        return studentId;
    }

    /**
     * Associação da mensagem ao ID do estudante.
     *
     * @param studentId - ID do estudante.
     *
     * @return referência do objeto.
     */
    public Message setStudentId(long studentId) {
        this.studentId = studentId;
        return this;
    }

    /**
     * Retorno do estado do estudante.
     *
     * @return estado do estudante.
     */
    public StudentState getStudentState() {
        return studentState;
    }

    /**
     * Associação do estado do estudante à mensagem.
     *
     * @param estado do estudante.
     *
     * @return referência do objeto.
     */
    public Message setStudentState(StudentState state) {
        this.studentState = state;
        return this;
    }

    /**
     * Retorna ao estado do Waiter.
     *
     * @return estado do waiter.
     */
    public WaiterState getWaiterState() {
        return waiterState;
    }

    /**
     * Associação do estado do Waiter à mesnagem.
     *
     * @param estado do waiter.
     *
     * @return referência do objeto.
     */
    public Message setWaiterState(WaiterState state) {
        this.waiterState = state;
        return this;
    }

    /**
     * Retorna o estado do Chef.
     *
     * @return estado do chef.
     */
    public ChefState getChefState() {
        return chefState;
    }

    /**
     * Associação do estado do chefe à mensagem.
     *
     * @param estado do chefe.
     *
     * @return referência ao objeto.
     */
    public Message setChefState(ChefState state) {
        this.chefState = state;
        return this;
    }

    /**
     * Retorna o número do prato.
     *
     * @return número do prato.
     */
    public int getNumberCourse() {
        return numberCourse;
    }

    /**
     * Associação do número do prato à mensagem.
     *
     * @param número do prato.
     *
     * @return referência ao objeto.
     */
    public Message setNumberCourse(int numberCourse) {
        this.numberCourse = numberCourse;
        return this;
    }

    /**
     * Retorna o caractér correspondente à tarefa do Waiter.
     *
     * @return tarefa
     */
    public char getTask() {
        return task;
    }

    /**
     * Associação do caractér à mensagem.
     *
     * @param caractér da tarefa.
     *
     * @return referência ao objeto.
     */
    public Message setTask(char task) {
        this.task = task;
        return this;
    }


    /**
     * Retorna a ordem de chegada.
     *
     * @return ordem de chegada.
     */
    public int getArrivalOrder() {
        return arrivalOrder;
    }

    /**
     * Associação da ordem de chegada à mensagem.
     *
     * @param ordem de chegada
     *
     * @return referência ao objeto.
     */
    public Message setArrivalOrder(int arrivalOrder) {
        this.arrivalOrder = arrivalOrder;
        return this;
    }


    /**
     * Retorna o parametro de everybodyFinished
     *
     * @return True ou False, dependendo do parametro.
     */
    public boolean getEverybodyChosen() {
        return everybodyChosen;
    }

    /**
     * Associação do parametro de everybodyChosen à mensagem.
     *
     * @param parametro everybodyChosen 
     *
     * @return referência ao objeto.
     */
    public Message setEverybodyChosen(boolean everybodyChosen) {
        this.everybodyChosen = everybodyChosen;
        return this;
    }


    /**
     * Retorna o parametro de tableAllStudentsServed
     *
     * @return True ou False, dependendo do parametro.
     */
    public boolean getTableAllStudentsServed() {
        return tableAllStudentsServed;
    }

    /**
     * Assocaição do parametro de tableAllStudentsServed à mensagem.
     *
     * @param tableAllStudentsServed
     *
     * @return referência ao objeto.
     */
    public Message setTableAllStudentsServed(boolean tableAllStudentsServed) {
        this.tableAllStudentsServed = tableAllStudentsServed;
        return this;
    }

    /**
     * Retorna o parametro de endEating
     *
     * @return True ou False, dependendo do parametro.
     */
    public boolean getEndEating() {
        return endEating;
    }


    /**
     * Asssociação do parametro endEating à mensagem.
     *
     * @param endEating
     *
     * @return referência ao objeto.
     */
    public Message setEndEating(boolean endEating) {
        this.endEating = endEating;
        return this;
    }

    /**
     * Retorna o parametro everybodyFinished
     *
     * @return True ou False, dependendo do parametro.
     */
    public boolean getEverybodyFinished() {
        return everybodyFinished;
    }

    /**
     * Associação do parametro everybodyFinished à mensagem.
     *
     * @param everybodyFinished 
     *
     * @return referência ao objeto.
     */
    public Message setEverybodyFinished(boolean everybodyFinished) {
        this.everybodyFinished = everybodyFinished;
        return this;
    }

    /**
     * Retorna o parametro de orderCompleted
     *
     * @return True ou False, dependendo do parametro.
     */
    public boolean getKitchenOrderCompleted() {
        return kitchenOrderCompleted;
    }

    /**
     * Associação do parametro de orderCompleted à mensagem.
     *
     * @param orderCompleted 
     *
     * @return referência ao objeto
     */
    public Message setKitchenOrderCompleted(boolean orderCompleted) {
        this.kitchenOrderCompleted = orderCompleted;
        return this;
    }

    /**
     * Retorna o parametro de allPortionsDelivered
     *
     * @return True ou False, dependendo do parametro.
     */
    public boolean getKitchenAllPortionsDelivered() {
        return kitchenAllPortionsDelivered;
    }

    /**
     * Associação do parametro de allPortionsDelivered à mensagem.
     *
     * @param allPortionsDelivered 
     *
     * @return referência ao objeto
     */
    public Message setKitchenAllPortionsDelivered(boolean allPortionsDelivered) {
        this.kitchenAllPortionsDelivered = allPortionsDelivered;
        return this;
    }
    
    public String toString() {

        StringBuilder sb = new StringBuilder(String.format("%n"));
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
               sb.append(f.getName());
                sb.append(" = ");
                sb.append(f.get(this));
                sb.append(String.format("%n"));
            } catch (IllegalAccessException e) {
            }
        }
        return sb.toString();
   }
    
    /**
     * Conversão para um string XML.
     *
     * @return string contendo a descrição XML da mensagem
     */
    public String toXMLString() {
    	return ("<Mensagem>"
                + "<Tipo>"
                + type
                + "</Tipo>"
                + "<studentId>"
                + studentId
                + "</studentId>"
                + "<numberCourse>"
                + numberCourse
                + "</numberCourse>"
                + "<studentState>"
                + studentState
                + "</studentState>"
                + "<waiterState>"
                + waiterState
                + "</waiterState>"
                + "<chefState>"
                + chefState
                + "</chefState>"
                + "<kitchenAllPortionsDelivered>"
                + kitchenAllPortionsDelivered
                + "</kitchenAllPortionsDelivered>"
                + "<kitchenOrderCompleted>"
                + kitchenOrderCompleted
                + "</kitchenOrderCompleted>"
                + "<task>"
                + task
                + "</task>"
                + "<arrivalOrder>"
                + arrivalOrder
                + "</arrivalOrder>"
                + "<everybodyChosen>"
                + everybodyChosen
                + "</everybodyChosen>"
                + "<tableAllStudentsServed>"
                + tableAllStudentsServed
                + "</tableAllStudentsServed>"
                + "<endEating>"
                + endEating
                + "</endEating>"
                + "<everybodyFinished>"
                + everybodyFinished
                + "</everybodyFinished>"
                + "</Mensagem>"); 
    }
}
