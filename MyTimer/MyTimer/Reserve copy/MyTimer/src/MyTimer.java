import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;


class RoundedBorder implements Border{
    int rad;
    RoundedBorder(int r) {this.rad=r;}

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
       g.drawRoundRect(x, y, width-1, height-1, rad, rad);}

    @Override
    public Insets getBorderInsets(Component c) {
    return new Insets(this.rad+1,this.rad+1,this.rad,this.rad);}

    @Override
    public boolean isBorderOpaque() {return true;}
}

class MainFrame implements ActionListener {
    JFrame Frame;
    JButton BtStart,BtStop,BtReset,BtContinue,BtCheckpoint;
    JCheckBox CheckBox1;
    JTextArea TextField; //TextArea!
    public static JLabel Lb;
    static int tm=0, ts=0,tms=0; 
    static boolean go=false;
    static boolean Multi=false;
    int width=300, height=350;
    String text;
   void run(){
    
           Frame=new JFrame("Таймер");
           Frame.setBounds(400,100,width,height);
           Frame.setLayout(null);    
           Frame.setVisible(true);
           Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
         Lb=new JLabel("00:00:00",JLabel.CENTER);
         Lb.setBounds(width/2-200-8,30,400,100);
         Font LbFont=new Font("Arial",Font.BOLD,60);
         Lb.setFont(LbFont);
         Frame.add(Lb);
         
         BtStart=new JButton("Start");
         BtStart.setFont(new Font("Arial",Font.BOLD,20));
         BtStart.setBounds(width/2-8-100,height-180,200,100);
         BtStart.setBorder(new RoundedBorder(10));
         BtStart.setFocusPainted(false);
         BtStart.addActionListener(this);
         BtStart.setActionCommand("BtStartPressed");
         Frame.add(BtStart);
         
         BtStop=new JButton("Stop");
         BtStop.setFont(new Font("Arial",Font.BOLD,20));
         BtStop.setBounds(width/2-8-100,height-180,200,100);
         BtStop.setBorder(new RoundedBorder(10));
         BtStop.setFocusPainted(false);
         BtStop.addActionListener(this);
         BtStop.setActionCommand("BtStopPressed");
         BtStop.setVisible(false);
         Frame.add(BtStop);
         
         BtReset=new JButton("Reset");
         BtReset.setFont(new Font("Arial",Font.BOLD,20));
         BtReset.setBounds(width/2-8-100,height-180,99,100);
         BtReset.setBorder(new RoundedBorder(10));
         BtReset.setFocusPainted(false);
         BtReset.addActionListener(this);
         BtReset.setActionCommand("BtResetPressed");
         BtReset.setVisible(false);
         Frame.add(BtReset);
         
         BtContinue=new JButton("Continue");
         BtContinue.setFont(new Font("Arial",Font.BOLD,17));
         BtContinue.setBounds(width/2-8+2,height-180,99,100);
         BtContinue.setBorder(new RoundedBorder(10));
         BtContinue.setFocusPainted(false);
         BtContinue.addActionListener(this);
         BtContinue.setActionCommand("BtContinuePressed");
         BtContinue.setVisible(false);
         Frame.add(BtContinue);
         
         BtCheckpoint=new JButton("Checkpoint");
         BtCheckpoint.setFont(new Font("Arial",Font.BOLD,17));
         BtCheckpoint.setBounds(width/2-8-100,height-180,99,100);
         BtCheckpoint.setBorder(new RoundedBorder(10));
         BtCheckpoint.setFocusPainted(false);
         BtCheckpoint.addActionListener(this);
         BtCheckpoint.setActionCommand("BtCheckpointPressed");
         BtCheckpoint.setVisible(false);
         Frame.add(BtCheckpoint);
         
         CheckBox1=new JCheckBox("A few measurements",false);
         CheckBox1.setBounds(BtStart.getX(),BtStart.getY()+BtStart.getHeight()+10, 150, 20);
        Frame.add(CheckBox1);
        CheckBox1.addItemListener(new ItemListener() {
            
        @Override
        public void itemStateChanged(ItemEvent e) {
            MainFrame.Multi=CheckBox1.isSelected();
            if (MainFrame.Multi==true) {
                
                TextField.setVisible(true);
                Frame.setSize(Frame.getWidth(),Frame.getHeight()+200);
            }
                else{
                TextField.setVisible(false);
                Frame.setSize(Frame.getWidth(),Frame.getHeight()-200);
            };
        }
    });
        TextField=new JTextArea();
        TextField.setBounds(width/2-143-3, BtStart.getHeight()+210, 286,210);
        TextField.setBorder(BorderFactory.createLoweredBevelBorder());
        TextField.setVisible(false);
        Frame.add(TextField);
        Frame.setResizable(false);
        Frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

   switch(e.getActionCommand()){
   case "BtStartPressed":
      this.go=true;
      this.BtStart.setVisible(false);
      this.BtStop.setVisible(true);
       Timer t=new Timer(); 
      long delay=10;
      Tsk task1=new Tsk();
      t.schedule(task1,delay);
      if (this.Multi==true){
      BtStop.setBounds(this.width/2-8+2,height-180,99,100);
      this.BtCheckpoint.setVisible(true);
      }
      break;
   case "BtCheckpointPressed":
       if (text==null) text=Lb.getText()+"\n";
       else text+=Lb.getText()+"\n";
     this.TextField.setText(text);
       break;
    case "BtStopPressed":
       this.go=false; 
       this.BtReset.setVisible(true);
       this.BtContinue.setVisible(true);
       this.BtStop.setVisible(false); 
       if (this.Multi==true) {
       if (text==null) text=Lb.getText()+"\n";
       else text+=Lb.getText()+"\n";
     this.TextField.setText(text);}
           break;
    case "BtResetPressed":
        this.tm=0; this.ts=0; this.tms=0;
        this.Lb.setText("00:00:00");
        this.BtStart.setVisible(true);
        this.BtReset.setVisible(false);
        this.BtContinue.setVisible(false); break;
    case "BtContinuePressed":
        this.go=true;
         t=new Timer(); 
      delay=10;
      task1=new Tsk();
      t.schedule(task1,delay);
      this.BtStop.setVisible(true);
      this.BtReset.setVisible(false);
      this.BtContinue.setVisible(false); break;
    }}
    }


class Tsk extends TimerTask{

    @Override
    public void run() {
       
    MainFrame MF=new MainFrame();
    if (MF.go==true){
        long time=System.currentTimeMillis();
    MF.tms++;
    if (MF.tms==100) {MF.ts++; MF.tms=0;}
    if (MF.ts==60) {MF.tm++; MF.ts=0;}
   
    String Tm=String.format("%1$02.0f",(double)MF.tm);
    String Ts=String.format("%1$02.0f",(double)MF.ts);
    String Tms=String.format("%1$02.0f",(double)MF.tms);
    MF.Lb.setText(Tm+":"+Ts+":"+Tms);
    Timer t=new Timer(); 
      long delay=10;
      Tsk task1=new Tsk();
     t.schedule(task1,delay); 
    }
    }}

public class MyTimer {

    public static void main(String[] args) {
       new MainFrame().run(); 
     
         
    }
    
}
