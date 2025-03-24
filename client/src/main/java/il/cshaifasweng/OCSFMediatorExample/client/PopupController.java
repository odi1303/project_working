package il.cshaifasweng.OCSFMediatorExample.client;


public interface PopupController<I, O> {
    void reInitialize(I input);
    O getOutput();
}
