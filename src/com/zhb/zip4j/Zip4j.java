package com.zhb.zip4j;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Zip4j extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_filepath;
	private JTextField tf_savepath;
	private JTextField tf_partsize;
	private JTextField tf_delPath;
	private JTextField tf_startTime;
	private JTextField tf_endTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Zip4j frame = new Zip4j();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Zip4j() {
		setTitle("文件压缩");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lb_filepath = new JLabel("压缩文件");
		lb_filepath.setBounds(56, 63, 61, 17);
		contentPane.add(lb_filepath);

		tf_filepath = new JTextField();
		tf_filepath.setBounds(160, 61, 303, 21);
		contentPane.add(tf_filepath);
		tf_filepath.setColumns(10);

		JButton bt_filepath = new JButton("选择");
		bt_filepath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(2);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					System.out.println((new StringBuilder("文件夹:")).append(
							file.getAbsolutePath()).toString());
					tf_filepath.setText(file.getAbsolutePath());
				} else if (file.isFile())
					System.out.println((new StringBuilder("文件:")).append(
							file.getAbsolutePath()).toString());
				System.out.println(jfc.getSelectedFile().getName());
			}
		});
		bt_filepath.setBounds(517, 58, 107, 27);
		contentPane.add(bt_filepath);

		JLabel lb_savepath = new JLabel("存放路径");
		lb_savepath.setBounds(56, 134, 61, 17);
		contentPane.add(lb_savepath);

		tf_savepath = new JTextField();
		tf_savepath.setBounds(160, 132, 303, 21);
		contentPane.add(tf_savepath);
		tf_savepath.setColumns(10);

		JButton bt_savepath = new JButton("选择");
		bt_savepath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(2);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					System.out.println((new StringBuilder("文件夹:")).append(
							file.getAbsolutePath()).toString());
					tf_savepath.setText(file.getAbsolutePath());
				} else if (file.isFile())
					System.out.println((new StringBuilder("文件:")).append(
							file.getAbsolutePath()).toString());
				System.out.println(jfc.getSelectedFile().getName());
			}
		});
		bt_savepath.setBounds(517, 129, 107, 27);
		contentPane.add(bt_savepath);

		JLabel lb_partsize = new JLabel("分卷大小");
		lb_partsize.setBounds(56, 201, 61, 17);
		contentPane.add(lb_partsize);

		tf_partsize = new JTextField();
		tf_partsize.setBounds(160, 199, 303, 21);
		contentPane.add(tf_partsize);
		tf_partsize.setColumns(10);

		JButton bt_zip = new JButton("压缩");
		bt_zip.addActionListener(new ActionListener() {
			private long splitLength;
			private String savepath;
			private String year;
			private String month;
			private String day;
			private String zippath;
			private String sourcepath;
			private String filepath;
			
			
			

			public void getDirectory(File file) {

				File flist[] = file.listFiles();
				if (flist == null || flist.length == 0) {
					return;
				}
				for (File f : flist) {
					if (f.isDirectory()) {
						// 这里将列出所有的文件夹
						System.out.println("Dir==>" + f.getAbsolutePath());
						System.out.println(f.getAbsolutePath().length());
						if (f.getAbsolutePath().length() == 34) {

							try {
								filepath = f.getAbsolutePath();
								String regex = "\\\\";
								String[] fp = filepath.split(regex);
								year = fp[6].substring(0, 4);
								month = fp[6].substring(4, 6);
								day = fp[6].substring(6, 8);
								sourcepath = (new StringBuilder(String
										.valueOf(fp[0]))).append(regex)
										.append(fp[1]).append(regex)
										.append(fp[2]).append(regex)
										.append(fp[3]).append(regex)
										.append(fp[4]).append(regex)
										.append(fp[5]).append(regex)
										.append(year).append(regex)
										.append(month).append(regex)
										.append(day).append(regex).toString();
								savepath = (new StringBuilder(String
										.valueOf(fp[0]))).append(regex)
										.append(fp[1]).append(regex)
										.append(fp[2]).append(regex)
										.append(fp[3]).append(regex)
										.append(fp[4]).append(regex)
										.append(fp[5]).append(regex)
										.append(year).append(regex)
										.append(month).append(regex)
										.append(day).append(regex)
										.append(fp[6]).append(".zip")
										.toString();
								File scpath = new File(sourcepath);
								zippath = (new StringBuilder(String
										.valueOf(fp[0]))).append(regex)
										.append(fp[1]).append(regex)
										.append(fp[2]).append(regex)
										.append(fp[3]).append(regex)
										.append(fp[4]).append(regex)
										.append(fp[5]).append(regex)
										.append(fp[6]).toString();
								// savepath = tf_savepath.getText().trim();
								if (!scpath.exists()) {
									if (scpath.mkdirs()) {
										System.out.println("创建目录" + scpath
												+ "成功！");

									} else {
										System.out.println("创建目录" + scpath
												+ "失败！");

									}
								}

								splitLength = Integer.parseInt(tf_partsize
										.getText()) * 1000 * 1000;

								ZipFile zf = new ZipFile(savepath);
								ZipParameters zp = new ZipParameters();
								zp.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
								zp.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FAST);
								zf.createZipFileFromFolder(zippath, zp, true,
										splitLength);
								System.out.println("success");
							} catch (ZipException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						getDirectory(f);
					} else {
						// 这里将列出所有的文件
						// System.out.println("file==>" + f.getAbsolutePath());
					}
				}

			}

			public void actionPerformed(ActionEvent e) {
				File file = new File((tf_filepath.getText()));
				getDirectory(file);
			}

		});
		bt_zip.setBounds(517, 196, 107, 27);
		contentPane.add(bt_zip);

		JLabel lb_delPath = new JLabel("删除文件");
		lb_delPath.setBounds(56, 266, 61, 17);
		contentPane.add(lb_delPath);

		tf_delPath = new JTextField();
		tf_delPath.setBounds(162, 264, 301, 21);
		contentPane.add(tf_delPath);
		tf_delPath.setColumns(10);

		JButton bt_delPath = new JButton("选择");
		bt_delPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(2);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file.isDirectory()) {
					System.out.println((new StringBuilder("文件夹:")).append(
							file.getAbsolutePath()).toString());
					tf_delPath.setText(file.getAbsolutePath());
				} else if (file.isFile())
					System.out.println((new StringBuilder("文件:")).append(
							file.getAbsolutePath()).toString());
				System.out.println(jfc.getSelectedFile().getName());
			}
		});
		bt_savepath.setBounds(517, 129, 107, 27);
		bt_delPath.setBounds(517, 261, 107, 27);
		contentPane.add(bt_delPath);

		JLabel lb_startTime = new JLabel("开始时间");
		lb_startTime.setBounds(56, 316, 61, 17);
		contentPane.add(lb_startTime);

		tf_startTime = new JTextField();
		tf_startTime.setBounds(160, 314, 173, 22);
		contentPane.add(tf_startTime);
		tf_startTime.setColumns(10);

		JLabel lb_endTime = new JLabel("结束时间");
		lb_endTime.setBounds(56, 364, 61, 17);
		contentPane.add(lb_endTime);

		tf_endTime = new JTextField();
		tf_endTime.setBounds(160, 362, 175, 19);
		contentPane.add(tf_endTime);
		tf_endTime.setColumns(10);

		JButton bt_delfile = new JButton("删除");
		bt_delfile.addActionListener(new ActionListener() {

			private long startTime;
			private long endTime;
			
			private String sourcepath;
			private String filepath;

			 private boolean deleteDir(File dir) {
			        if (dir.isDirectory()) {
			            String[] children = dir.list();
//递归删除目录中的子目录下
			            for (int i=0; i<children.length; i++) {
			                boolean success = deleteDir(new File(dir, children[i]));
			                if (!success) {
			                    return false;
			                }
			            }
			        }
			        // 目录此时为空，可以删除
			        return dir.delete();
			    }
			
			
			
			
			private void getDirectory(File file) {
				File flist[] = file.listFiles();
				if (flist == null || flist.length == 0) {
					return;
				}
				for (File f : flist) {
					if (f.isDirectory()) {
						// 这里将列出所有的文件夹
						System.out.println("Dir==>" + f.getAbsolutePath());
						
						if (f.getAbsolutePath().length() == 34) {

							filepath = f.getAbsolutePath();
							String regex = "\\\\";
							String[] fp = filepath.split(regex);
//								year = fp[6].substring(0, 4);
//								month = fp[6].substring(4, 6);
//								day = fp[6].substring(6, 8);
							sourcepath = (new StringBuilder(String
									.valueOf(fp[0]))).append(regex)
									.append(fp[1]).append(regex)
									.append(fp[2]).append(regex)
									.append(fp[3]).append(regex)
									.append(fp[4]).append(regex)
									.append(fp[5]).append(regex)
									.append(fp[6]).append(regex)
									.toString();
							
							startTime = Integer.parseInt(tf_startTime.getText());
							endTime =  Integer.parseInt(tf_endTime.getText());
							if(  startTime <= Integer.parseInt(fp[6].toString())  &&  Integer.parseInt(fp[6].toString()) <=  endTime){
								File delFile = new File(sourcepath);
								deleteDir(delFile);        
							}
							
							// savepath = tf_savepath.getText().trim();

							System.out.println("success");
						}
						
						
						getDirectory(f);
					} else {
						// 这里将列出所有的文件
						// System.out.println("file==>" + f.getAbsolutePath());
					}
				}
			}

			public void actionPerformed(ActionEvent e) {

				File file = new File((tf_delPath.getText()));
				getDirectory(file);

			}
		});
		bt_delfile.setBounds(517, 339, 107, 27);
		contentPane.add(bt_delfile);

	}
}
