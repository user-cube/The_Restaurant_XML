package commonStructures;

/**
 * Extensão do FIFO para que possa ser devidamente sinalizado com recurso a monitores
 * de modo a que não existam problemas no armazenamento dos caracteres num programa concorrencial.
 * 
 * @author Rui Coelho e Duarte Castanho
 * @param <T> 
 */
public class FIFO<T> {
	/**
	 * Valores do FIFO.
	 */
    protected T[] values;
    /**
     * Verificação se o FIFO está vazio.
     */
    protected boolean empty;
    /**
     * Tamanho do FIFO.
     */
    protected int current_size;
    /**
     * Indíce do elemento removido.
     */
    protected int outPnt;
    /**
     * Idíce do elemento disponível.
     */
    protected int inPnt;

    @SuppressWarnings("unchecked")
    public FIFO(int size) {
        empty = true;
        current_size = 0;
        outPnt = 0;
        inPnt = 0;
        if (size > 0) {
            this.values = (T[]) new Object[size];
        } else {
            this.values = (T[]) new Object[10];
        }

    }

    /**
     * Retorna o número de elementos no FIFO.
     *
     * @return número de elementos no FIFO.
     */
    public int getSize() {
        return this.current_size;
    }

    /**
     * Inserção de elementos no FIFO.
     *
     * @param elemento a inserir no FIFO.
     */
    public void insert(T element) {
        if ((this.inPnt == this.outPnt) && !empty) {
            System.out.println("ERROR: QUEUE IS FULL");
            return;
        }
        else {
            empty = false;
            this.current_size++;
            this.values[this.inPnt] = element;
            this.inPnt = (this.inPnt + 1) % this.values.length;
        }
    }

    /**
     * Remoção e retorno do elemento mais antigo do FIFO (elemento que foi adicionado primeiro).
     * Caso contrário retorna null.
     *
     * @return primeiro elemento inserido ou null.
     */
    public T remove() {
        if (!empty) {
            this.current_size--;
            T element = this.values[outPnt];
            this.values[outPnt] = null;
            this.outPnt = (this.outPnt + 1) % this.values.length;
            // Which means we removed last element
            if (this.outPnt == this.inPnt) {
                empty = true;
            }
            return element;
        }

        return null;
    }
}
