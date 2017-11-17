package io.github.xdiamond.client.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class XDException extends Exception {

    public XDException(){
        super();
    }

    public XDException(String msg){
        super(msg);
    }

    public XDException(String msg,Throwable t){
        super(msg,t);
    }

    public XDException(Throwable t){
        super(t);
    }
}

