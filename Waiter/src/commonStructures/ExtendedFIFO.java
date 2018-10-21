package commonStructures;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ExtendedFIFO<T> extends FIFO<T> {
    private Lock mutex;
    private Condition emptyLock;
    private Condition fullLock;

    public ExtendedFIFO(int size) {
        super(size);
        mutex = new ReentrantLock();
        emptyLock = mutex.newCondition();
        fullLock = mutex.newCondition();
    }

    /**
     * Versão concorrencial de inserção de elementos.
     */
    @Override
    public void insert(T element) {
        mutex.lock();
        try {
            while (this.current_size == this.values.length) {
                try {
                    fullLock.await();
                } catch (InterruptedException e) { }
            }
            this.current_size++;
            this.values[this.inPnt] = element;
            this.inPnt = (this.inPnt + 1) % this.values.length;

            emptyLock.signal();
        } finally {

            mutex.unlock();
        }
    }


    /**
     * Versão concorrencial de remoção de elementos do FIFO.
     */
    @Override
    public T remove() {
        mutex.lock();
        try {
            while (current_size == 0) {
                try {
                    emptyLock.await();
                } catch (InterruptedException e) {
                }
            }
            this.current_size--;
            T element = this.values[outPnt];
            this.values[outPnt] = null;
            this.outPnt = (this.outPnt + 1) % this.values.length;
            fullLock.signal();
            return element;
        } finally {
            mutex.unlock();
        }
    }
}
