package HoustoN.Sarafan.domain;

public final class Views {
    public interface Id {}

    public interface IdName extends Id {}

    public interface FullComment extends IdName {}

    public interface FullMessage extends FullComment {}

    public interface FullProfile extends IdName {}

    public interface FullChat extends IdName {}
}
