
import Database.Database;
import Entities.ApplicationContext;
import Views.FrmMain;
import Views.Components.LoginDialog;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author Ellian
 */
public class HardwareSIG {
    private static Database db;

    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } 
        catch (UnsupportedLookAndFeelException e) {
            System.out.println("Look and Feel error: "+e);
        }
        if (!openConnection()) {
            JOptionPane.showMessageDialog(null,"Erro ao conectar no banco de dados!");
            return;          
        }
        
        LoginDialog login = new LoginDialog(null, true);
        login.setResizable(false);
        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        login.setVisible(true);
        
        if (ApplicationContext.getInstance().getActiveUser()==null) return;

        JFrame frame = new FrmMain();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int)Math.round(screenSize.width*0.5), (int) Math.round(screenSize.height*0.5));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);    
    }
    
    public static boolean openConnection(){
        db = new Database();
        try {
            ApplicationContext.getInstance().setConnection(db.getConnection());
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
