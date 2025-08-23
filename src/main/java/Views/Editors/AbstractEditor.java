/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views.Editors;

import Controllers.ControllerInterface;
import Entities.ApplicationContext;
import Utils.ViewUtils;
import Views.Panes.PaneInterface;
import java.awt.Container;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Ellian
 */
abstract class AbstractEditor<T> extends JDialog{
    protected ApplicationContext context;
    protected PaneInterface vinculatedPanel;
    protected ControllerInterface<T> controller;
    protected T editingItem;
    
    public AbstractEditor(Frame owner, boolean modal) {
        super(owner, modal);
        context = ApplicationContext.getInstance();
        vinculatedPanel = context.getActivePanel();
    }
    
    protected <C extends Container> void saveItemAfterEditingAction(C container) {
        if (ViewUtils.missingField(container)) {
            JOptionPane.showMessageDialog(container, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;        
        }
        
        setItem();
        
        boolean success = controller.saveItem(editingItem);
        
        if (!success) {
            JOptionPane.showMessageDialog(null, "Erro desconhecido ao salvar dados!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Cadastro realizado!", "Sucesso", JOptionPane.PLAIN_MESSAGE);        
        
        if (!(vinculatedPanel == null)) {
            vinculatedPanel.loadTable();
        }
        
        dispose();        
    };
    
    protected abstract void setItem();
    public abstract void fillFields(T arg);
}
