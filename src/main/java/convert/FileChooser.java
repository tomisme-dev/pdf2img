package convert;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class FileChooser extends JFrame implements ActionListener {
    JButton open=null;
    public static void main(String[] args) {
        new FileChooser();
    }
    public FileChooser(){
        open=new JButton("open");
        this.add(open);
        this.setBounds(400, 200, 500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        open.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(true);
        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || (f.isFile() && f.getName().endsWith(".pdf"));
            }

            @Override
            public String getDescription() {
                return "pdf文件";
            }
        });
        jfc.showDialog(new JLabel(), "选择");
        File[] selectedFiles = jfc.getSelectedFiles();
//       未选中时, 直接结束
        if(selectedFiles == null || selectedFiles.length < 1) {
            return;
        }

        System.out.printf("select文件(个): "+ Arrays.toString(selectedFiles));
//        执行转换
        Arrays.stream(selectedFiles).forEach(file -> {
            Main.convertingCount.addAndGet(1);
            MyThreadPoolExecutorFactory.getExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Pdf2Img.pdf2Image(file.getAbsolutePath(), 300);
                }
            });
        });
    }

    private void showMessage() {
        JOptionPane.showMessageDialog(null, "请重新选择一个pdf文件");
        this.open.doClick();
    }


}
