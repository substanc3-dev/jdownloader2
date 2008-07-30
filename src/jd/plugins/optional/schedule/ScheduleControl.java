package jd.plugins.optional.schedule;

import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import jd.utils.JDLocale;

public class ScheduleControl extends JDialog implements ActionListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton add = new JButton(JDLocale.L("addons.schedule.menu.add","Add"));
    Choice list = new Choice();
    JPanel menu = new JPanel();
    JPanel panel = new JPanel();    
    JButton remove = new JButton(JDLocale.L("addons.schedule.menu.remove","Remove"));
       
    JButton show = new JButton(JDLocale.L("addons.schedule.menu.edit","Edit"));
    Timer status = new Timer(1,this);

    Vector<ScheduleFrame> v = new Vector<ScheduleFrame>();
    
    boolean visible = false;
    
    public ScheduleControl(){
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {                
                    setVisible(false);
                    status.stop();
                }
            });
            
            this.setTitle("Scheduler by Tudels");
            this.setSize(450, 300);
            this.setResizable(false);
            this.setLocation(300, 300);
            
            this.menu.setLayout(new FlowLayout());
            this.menu.add(new JLabel("          "));
            this.menu.add(this.list);
            this.list.add(JDLocale.L("addons.schedule.menu.create","Create"));
            this.menu.add(this.show);
            this.menu.add(this.add);
            this.menu.add(this.remove);
            this.menu.add(new JLabel("          "));
           
            this.getContentPane().setLayout(new FlowLayout());
            this.getContentPane().add(menu);
            this.getContentPane().add(panel);

            
            this.add.addActionListener(this);
            this.remove.addActionListener(this);
            this.show.addActionListener(this);
                       
            SwingUtilities.updateComponentTreeUI(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == add){
            int a = v.size() + 1;
            this.v.add(new ScheduleFrame("Schedule " + a));
            reloadList();
            SwingUtilities.updateComponentTreeUI(this);           
        }
        
        if(e.getSource() == remove){
            try{
//                ScheduleFrame s = (ScheduleFrame) v.elementAt(list.getSelectedIndex());
            
                this.v.remove(list.getSelectedIndex());
                this.reloadList();
                this.panel.removeAll();
                renameLabels();
                SwingUtilities.updateComponentTreeUI(this);
            }catch(Exception ex){}
        }
        
        if (e.getSource() == show){
            try{
                int item = this.list.getSelectedIndex();
                ScheduleFrame sched;
            
                if(visible == false){
                    this.status.stop();
                    this.panel.removeAll();
                    sched = v.elementAt(item);
                    visible = true;
                    this.panel.add(sched);
                    this.show.setText(JDLocale.L("addons.schedule.menu.close","Close"));
                    this.controls(false);
                }
                else{
                    visible = false;
                    this.show.setText(JDLocale.L("addons.schedule.menu.edit","Edit"));
                    this.panel.removeAll();
                    this.status.start();
                    this.controls(true);
                }
                SwingUtilities.updateComponentTreeUI(this);
            }catch(Exception ex){}
        }
        
        if (e.getSource() == status){
            
            int size = v.size();
            
            this.panel.removeAll();
            this.panel.setLayout(new GridLayout(size,1));
            for(int i = 0; i < size; ++i){
                ScheduleFrame s = v.elementAt(i);
                int a = i+1;
                this.panel.add(new JLabel("Schedule "+a+" Status: "+s.status.getText()));
            }
            SwingUtilities.updateComponentTreeUI(this);
            
        }
        }
    
    
    public void controls(boolean b){

            this.add.setEnabled(b);
            this.remove.setEnabled(b);
            this.list.setEnabled(b);

    }
    public void reloadList(){
        
        this.list.removeAll();
        int size = v.size();
        
        String[] s = new String[size+10];
        
        for(int i = 1; i <= size; ++i){
            s[i] = " Schedule "+i;
            this.list.add(s[i]);
        }
        if(size == 0){
            this.list.add("Create");
        }

    }
    public void renameLabels(){
        int size = v.size();
        for(int i = 0; i < size; ++i){
            ScheduleFrame s = v.elementAt(i);
            s.label.setText(list.getItem(i));
        }
        
    }
}
