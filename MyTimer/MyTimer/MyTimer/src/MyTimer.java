import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.File;



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
    JMenuBar Menu;
    JButton BtStart,BtStop,BtReset,BtContinue,BtCheckpoint,BtSave;
    JCheckBox CheckBox1;
    JTextArea ta; 
    JLabel Lb1,Lb2; //labels for timer
    public static JLabel Lb;
    static int tm=0, ts=0,tms=0, TimerTime=0; 
    static boolean go=false;
    static boolean Multi=false, TimerMode=false;
    int width=300, height=350;
    String text;
    JScrollPane scroll;
    private Component modalToComponent;
    static JTextField TFM,TFS,TFMS; //text fields for minutes,secs,msecs
   void run(){
           
           Frame=new JFrame("Таймер");
           Frame.setBounds(400,100,width,height);
           Frame.setLayout(null);    
           Frame.setVisible(true);
           Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
           
         Menu=new JMenuBar();
         Menu.setBounds(0,0,Frame.getWidth(),20);
         
         //Menu.setBorder(new RoundedBorder(2));
         JMenu Mode=new JMenu("Mode");
         JMenuItem Sec=new JMenuItem("Stopwatch");
         Sec.addActionListener(this);
         Sec.setActionCommand("SecSelected");
         JMenuItem Timer=new JMenuItem("Timer");
         Timer.addActionListener(this);
         Timer.setActionCommand("TimerSelected");
         
         Menu.add(Sec); Menu.add(Timer);
         Frame.add(Menu);
         
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
         //BtStop setBounds are in "case" block
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
         BtCheckpoint.setFont(new Font("Arial",Font.BOLD,13));
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
       
     JPanel Panel = new JPanel ();
    //Panel.setBorder ( new TitledBorder ( new EtchedBorder (), "Multiple measurement" ) );
    Panel.setBounds(width/2-143-3, BtStart.getHeight()+217, 286,210);
    Panel.setVisible(false);
    ta = new JTextArea ( 12, 24 );
    ta.setEditable ( false );
    ta.setVisible(false);
        JScrollPane scroll = new JScrollPane (ta);
    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
    Panel.add ( scroll );
    Frame.add ( Panel );
    
    BtSave=new JButton("Save"); 
    BtSave.setBounds(width-90,BtStart.getY()+110,70,30);
    BtSave.setBorder(new RoundedBorder(10));
    BtSave.setFocusPainted(false);
    BtSave.addActionListener(this);
    BtSave.setActionCommand("BtSavePressed");
    BtSave.setVisible(false);
    Frame.add(BtSave);
    
    int twidth=50, theight=40, indent=25;
    TFM=new JTextField();
    TFS=new JTextField();
    TFMS=new JTextField();
    TFM.setBounds(Frame.getWidth()/2-8-twidth-indent-twidth/2,70,twidth,theight);
    TFM.setBorder(new RoundedBorder(5));
    TFM.setFont(new Font("Arial",Font.BOLD,36));
    TFM.setVisible(false);
    TFM.setText("00");
    Frame.add(TFM);
    TFS.setBounds(Frame.getWidth()/2-8-twidth/2,70,twidth,theight);
    TFS.setBorder(new RoundedBorder(5));
    TFS.setFont(new Font("Arial",Font.BOLD,36));
    TFS.setVisible(false);
    TFS.setText("00");
    Frame.add(TFS);
    TFMS.setBounds(Frame.getWidth()/2-8+indent+twidth/2,70,twidth,theight);
    TFMS.setBorder(new RoundedBorder(5));
    TFMS.setFont(new Font("Arial",Font.BOLD,36));
    TFMS.setVisible(false);
    TFMS.setText("00");
    Frame.add(TFMS);
    
    Lb1=new JLabel(":");
    Lb1.setBounds(Frame.getWidth()/2-8-indent-twidth/2+5,70,twidth,theight);
    Lb1.setFont(new Font("Arial",Font.BOLD,36));
    Lb1.setVisible(false);
    Frame.add(Lb1);
    Lb2=new JLabel(":");
    Lb2.setBounds(Frame.getWidth()/2-8+twidth/2+5,70,twidth,theight);
    Lb2.setFont(new Font("Arial",Font.BOLD,36));
    Lb2.setVisible(false);
    Frame.add(Lb2);
    
    
    CheckBox1.addItemListener(new ItemListener() { 
        @Override
        public void itemStateChanged(ItemEvent e) {
            MainFrame.Multi=CheckBox1.isSelected();
            if (MainFrame.Multi==true) {
                
                Panel.setVisible(true);
                BtSave.setVisible(true);
                ta.setVisible(true);
                Frame.setSize(Frame.getWidth(),Frame.getHeight()+200);
            }
                else{
                Panel.setVisible(false);
                BtSave.setVisible(false);
                ta.setVisible(false);
                Frame.setSize(Frame.getWidth(),Frame.getHeight()-200);
            };
        }
    });
        
        Frame.setResizable(false);
        Frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

   switch(e.getActionCommand()){
   case "BtStartPressed":
      try {if (TimerMode==true){tms=Integer.parseInt(TFMS.getText())*10;
      ts=Integer.parseInt(TFS.getText()); tm=Integer.parseInt(TFM.getText());}
      }catch(NumberFormatException ne){
      JOptionPane.showMessageDialog(null,"Symbol in entered, and number id needed.","Error!",JOptionPane.ERROR_MESSAGE);   break;} 
      this.go=true;
      this.BtStart.setVisible(false);
      this.BtStop.setVisible(true);
      BtStop.setBounds(width/2-8-100,height-180,200,100);
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
       this.BtReset.setVisible(false);
       this.BtContinue.setVisible(false);
       this.BtStop.setVisible(true); 
       if (this.text==null) this.text=Lb.getText()+" \n";
       else this.text+=Lb.getText()+" \n";
     this.ta.setText(this.text);
       break;
    case "BtStopPressed":
       this.go=false; 
       this.BtReset.setVisible(true);
       this.BtContinue.setVisible(true);
       this.BtStop.setVisible(false); 
       if (this.Multi==true) {
       if (this.text==null) this.text=Lb.getText()+" \n";
       else this.text+=Lb.getText()+" \n";
     this.ta.setText(this.text);}
           break;
    case "BtResetPressed":
        this.tm=0; this.ts=0; this.tms=0;
        this.Lb.setText("00:00:00");
        this.BtStart.setVisible(true);
        this.BtReset.setVisible(false);
        this.BtContinue.setVisible(false); 
        if (this.Multi==true) {this.text=null;
        this.ta.setText(this.text);}
        this.BtCheckpoint.setVisible(false);
        this.BtStop.setVisible(false);
        TFM.setText("00"); TFS.setText("00"); TFMS.setText("00");
            break;
    case "BtContinuePressed":
        this.go=true;
         t=new Timer(); 
      delay=10;
      task1=new Tsk();
      t.schedule(task1,delay);
      this.BtStop.setVisible(true);
      this.BtReset.setVisible(false);
      this.BtContinue.setVisible(false); break;
        
    case "BtSavePressed":
        JFileChooser fileChooser = new JFileChooser();    
if (fileChooser.showSaveDialog(modalToComponent) == JFileChooser.APPROVE_OPTION) {
  File file = fileChooser.getSelectedFile();
  try(FileOutputStream fos=new FileOutputStream(file.getAbsolutePath()))
        {byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        }catch(IOException ex){ 
            System.out.println(ex.getMessage());}} break;
   
    case "SecSelected":
        TimerMode=true;
        TFM.setVisible(false);
        TFS.setVisible(false);
        TFMS.setVisible(false);
        Lb1.setVisible(false);
        Lb2.setVisible(false);
        Lb.setVisible(true);
        CheckBox1.setVisible(true);
        break; 
    case "TimerSelected":
        TimerMode=true;
        TFM.setVisible(true);
        TFS.setVisible(true);
        TFMS.setVisible(true);
        Lb1.setVisible(true);
        Lb2.setVisible(true);
        Lb.setVisible(false);
        CheckBox1.setVisible(false);
        CheckBox1.setSelected(false);
        break;      
   }}}


class Tsk extends TimerTask{

    @Override
    public void run() {
       
    MainFrame MF=new MainFrame();
    if (MF.go==true&MF.TimerMode==false){       
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
    
    else {if (MF.go==true){
    if (MF.tms<=0&MF.ts==0&MF.tm==0){MF.go=false; JOptionPane.showMessageDialog(null, "Time is over!");} 
    else {if (MF.tms==0&MF.ts==0) {MF.tm--; MF.ts=59; MF.tms=1000;}
    else if (MF.tms==0) {MF.ts--; MF.tms=1000;}}
    if (MF.go==true) MF.tms=MF.tms-10;
    String Tm=String.format("%1$02.0f",(double)MF.tm);
    String Ts=String.format("%1$02.0f",(double)MF.ts);
    String Tms=String.format("%1$02.0f",(double)MF.tms/10);
    MF.TFM.setText(Tm);
    MF.TFS.setText(Ts);
    MF.TFMS.setText(Tms);
    Timer t=new Timer(); 
      long delay=10;
      Tsk task1=new Tsk();
     t.schedule(task1,delay); 
    }}
    }}

public class MyTimer {

    public static void main(String[] args) {
       new MainFrame().run(); 
     
         
    }
    
}
