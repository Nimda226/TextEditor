package sample;

public class IOResult<T> {
    //Data с Т потому что неизвестен тип данных generic
    private T data;
    private boolean ok;

    public IOResult(boolean ok, T data) {
        this.ok = ok;
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public boolean hasData() {
        return data != null;
    }

    public T getData() {
        return data;
    }
}