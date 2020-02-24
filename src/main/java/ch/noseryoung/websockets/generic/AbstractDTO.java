package ch.noseryoung.websockets.generic;

public abstract class AbstractDTO {

    protected String id;

    public String getId() {
        return id;
    }

    public AbstractDTO setId(String id) {
        this.id = id;
        return this;
    }
}
