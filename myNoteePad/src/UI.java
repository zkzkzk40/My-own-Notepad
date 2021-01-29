import javax.swing.*;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class UI extends JFrame implements ActionListener{
  //由打开按钮获取的文件位置
  String PATH=null;
  //默认字体
  Font font=new Font("宋体",Font.BOLD,22);
  //菜单栏
  private JMenuBar	  mb=new JMenuBar();
  //"文件"菜单
  private FgMenu	  mFile=new FgMenu("文件(F)",KeyEvent.VK_F);
  private JMenuItem   miNew=new JMenuItem("新建(N)",KeyEvent.VK_N),
                      miOpen=new JMenuItem("打开(O)...", KeyEvent.VK_O),
                      miSave=new JMenuItem("保存(S)",KeyEvent.VK_S),
                      miFont=new JMenuItem("字体与颜色(F)...",KeyEvent.VK_F),
                      miQuit=new JMenuItem("退出(X)",KeyEvent.VK_X);
  private JToolBar jToolBar=new JToolBar();
  private JTextArea jTextArea=new JTextArea();
  private JScrollPane jScrollPane=new JScrollPane(jTextArea);
  FgButton[] btn={new FgButton(new ImageIcon(ImageScaling.zoom("第七次上机\\resource\\new.png",0.1)),
                  "新建文件"),
                  new FgButton(new ImageIcon(ImageScaling.zoom("第七次上机\\resource\\open.png",0.1)),
                  "打开文件"),
                  new FgButton(new ImageIcon(ImageScaling.zoom("第七次上机\\resource\\save.png",0.16)),
                  "保存文件")
  };

  UI(String title){
    super(title);
    setSize(800,600);
    centerWindow();
    addMenus();
    addToolBar();
    add(jScrollPane);
    jTextArea.setFont(font);
  }
  private void addMenus() {
    //②：添加组件。本例中直接添加菜单
    setJMenuBar(mb);
    mFile.add(miNew);//新建
    miNew.addActionListener(this);
    mFile.add(miOpen);//打开
    miOpen.addActionListener(this);
    mFile.add(miSave);//保存
    miSave.addActionListener(this);
    mFile.addSeparator();//分割条
    mFile.add(miFont);//字体与颜色菜单
    miFont.addActionListener(this);
    mFile.addSeparator();//分割条
    mFile.add(miQuit);//退出
    miQuit.addActionListener(this);
    mb.add(mFile); //将"文件"菜单添加到菜单栏上
  }
  private void addToolBar(){
    //工具条
    Container c=getContentPane();
    c.add(BorderLayout.NORTH, jToolBar);
    jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));

    for(int i=0;i<btn.length;i++){
      btn[i].addActionListener(this);
      btn[i].setBorder(BorderFactory.createEmptyBorder());
      jToolBar.add(btn[i]);
    }
    //设置不可浮动
    jToolBar.setFloatable(false);

  }
  private void centerWindow(){
    //获得显示屏桌面窗口的大小
    Toolkit tk=getToolkit();
    Dimension dm=tk.getScreenSize();
    //让窗口居中显示
    setLocation((int)(dm.getWidth()-getWidth())/2,(int)(dm.getHeight()-getHeight())/2);
  }
  private String getTXT(){
    return jTextArea.getText();
  }
  private void setText(String text){
    jTextArea.setText(text);
  }
  private String chooseOpenFile(){
    JFileChooser fd=new JFileChooser("D:\\desktop");
    fd.showOpenDialog(null);
    File f=fd.getSelectedFile();
    return f.toString();
  }
  private String chooseSaveFile(){
    JFileChooser fd=new JFileChooser("D:\\desktop");
    fd.showSaveDialog(null);
    File f=fd.getSelectedFile();
    return f.toString();
  }

  private String newBuildFile(){
    JFileChooser fd=new JFileChooser();
    fd.showSaveDialog(null);
    File f=fd.getSelectedFile();
    return f.toString();
  }
  private void saveFile(String path){
    TXT.writeTXT(path,getTXT());
  }
  public static void main(String[] args) {
    UI ui=new UI("记事本");
    ui.setVisible(true);
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    //新建 清空
    if(e.getSource()==btn[0]||e.getSource().equals(miNew)){
      jTextArea.setText("");
    }
    //打开 选择位置->打开并读取
    else if(e.getSource()==btn[1]||e.getSource()==miOpen){
      PATH=chooseOpenFile();
      String txt=TXT.readTXT(PATH);
      setText(txt);
    }
    //保存 保存内容至文件
    else if(e.getSource()==btn[2]||miSave.equals(e.getSource())){
      //未打开过文件
      if(PATH==null){//PATH是成员变量,存储路径
        PATH=chooseSaveFile();//选择文件的窗口并返回路径
      }
      saveFile(PATH);//保存txt文件
      JOptionPane.showMessageDialog(null, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);

    }
    else if(e.getSource()==miQuit){
      dispose();
    }
    else if(e.getSource()==miFont){
      //字体选择
      fontSetDialog fsd=new fontSetDialog(this);
      fsd.setVisible(true);
      font=fsd.get_font();
      jTextArea.setFont(font);

      //颜色选择
      DefaultColorSelectionModel Model=new DefaultColorSelectionModel();
      JColorChooser jColorChooser=new JColorChooser(Model);
      Color color=jColorChooser.getColor();
      Color color1=JColorChooser.showDialog(null,"颜色选择",color);
      jTextArea.setForeground(color1);

    }
  }
}
class FgMenu extends JMenu{
  public FgMenu(String label){
    super(label);
  }
  public FgMenu(String label,int nAccelerator){
    super(label);
    setMnemonic(nAccelerator);
  }
}
class FgButton extends JButton{
  String path;
  public String getPath() {
    return path;
  }
  public FgButton(){
    super();
  }
  public FgButton(Icon icon){
    super(icon);
  }
  public FgButton(Icon icon,String strToolTipText){
    super(icon);
    setToolTipText(strToolTipText);
  }
  public FgButton(String text){
    super(text);
  }
  public FgButton(String text, Icon icon, String strToolTipText){
    super(text, icon);
    setToolTipText(strToolTipText);
  }

}
class fontSetDialog extends Dialog implements ActionListener{
  Font _font;
  JComboBox cbxFont=new JComboBox();
  JComboBox cbxFontSize=new JComboBox();//字体大小
  JButton okBtn = new JButton("确定");
  fontSetDialog(UI parent){
    super(parent,"字体颜色选择",true);
    setLocationRelativeTo(parent);
    InitFonts();
    okBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String fontName=cbxFont.getSelectedItem().toString();
        fontName=fontName.substring(fontName.indexOf("  |  ")+5);
        System.out.println(fontName);
        int size=cbxFontSize.getSelectedIndex()+9;
        _font=new Font(fontName,Font.BOLD,size);
        dispose();
      }
    });
  }
  private void InitFonts(){
    setSize(300,120);

    Container c = new Container();
    this.add(c);
    c.setLayout(new FlowLayout(FlowLayout.LEFT));

    c.add(new JLabel("字体名称："));
    c.add(cbxFont);
    c.add(new JLabel("字体大小："));
    c.add(cbxFontSize);
    c.add(okBtn);
//获得系统的字体数组
    GraphicsEnvironment ge= GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[]  fontList=ge.getAvailableFontFamilyNames();
    int i;

//添加字体名称
    for(i=0;i<fontList.length;i++) {
      cbxFont.addItem(String.valueOf(i)+"  |  "+fontList[i]);
    }

    cbxFont.setSelectedIndex(231);//选择index为231的项
//添加字体大小
    for(i=9;i<=72;i++) {
      cbxFontSize.addItem(new Integer(i).toString());
    }
    cbxFontSize.setSelectedIndex(11);//选择index为11的项
  }
  Font get_font(){return _font;}
  @Override
  public void actionPerformed(ActionEvent e) {
  }
}
