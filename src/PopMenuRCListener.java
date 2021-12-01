import java.awt.event.*;
import javax.swing.*;

//the purpose of this class is to be a custom mouse listener, since I wasn't sure how to use the default mouse listener

public class PopMenuRCListener extends MouseAdapter{

    public JPopupMenu popop;


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            popit(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {  //some systems use press, some use release, just use both
        if (e.isPopupTrigger())
            popit(e);
    }

    private void popit(MouseEvent e) {
        popop.show(e.getComponent(), e.getX(), e.getY());
    }

}



