 Copyright SHSBNU_China 2019 iGEM

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

package pkser;

import java.awt.EventQueue;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class User {
	private JFileChooser fDialog = new JFileChooser();
	private ArrayList<Integer> bpinds=new ArrayList<Integer>();
	private ArrayList<Integer> hiinds=new ArrayList<Integer>();
	private ArrayList<String> strs=new ArrayList<String>();
	private String str;
	private int ind;
	private JFrame frmBioToolkit;
	private int iter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User window = new User();
					window.frmBioToolkit.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public User() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBioToolkit = new JFrame();
		frmBioToolkit.setTitle("Bio Toolkit");
		frmBioToolkit.setIconImage(Toolkit.getDefaultToolkit().getImage(User.class.getResource("/pkser/logosmall~2.ico")));
		frmBioToolkit.setBounds(100, 100, 597, 476);
		frmBioToolkit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBioToolkit.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Please text base sequence or load from a .txt file:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(14, 5, 432, 18);
		frmBioToolkit.getContentPane().add(lblNewLabel);
		
		JLabel lblOrLoadFrom = new JLabel("Ready.");
		lblOrLoadFrom.setBounds(78, 178, 308, 18);
		frmBioToolkit.getContentPane().add(lblOrLoadFrom);

		TextArea textArea = new TextArea();
		textArea.setBounds(14, 29, 555, 143);
		frmBioToolkit.getContentPane().add(textArea);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(14, 178, 72, 18);
		frmBioToolkit.getContentPane().add(lblStatus);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		textArea_1.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(textArea_1); 
		scroll.setVerticalScrollBarPolicy( 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		scroll.setBounds(14, 205, 555, 211);
		//frmBioToolkit.getContentPane().add(textArea_1);
		frmBioToolkit.getContentPane().add(scroll);
		
		//JScrollPane JSP=new JScrollPane(textArea_1);
		//frmBioToolkit.add(JSP);
		
		JButton btnView = new JButton("View...");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				fDialog.setDialogTitle("Please select");
				int returnVal = fDialog.showOpenDialog(null);
				if(JFileChooser.APPROVE_OPTION == returnVal){
					try {
						BufferedReader bufferedReader = new BufferedReader(new FileReader(fDialog.getSelectedFile()));
						String line =bufferedReader.readLine();
						
				        while (bufferedReader.readLine()!=null){
				            line +=bufferedReader.readLine();
				        }
						textArea.setText(line); 
						bufferedReader.close();
					}
					catch (IOException e1) {
						lblOrLoadFrom.setText(e1.getMessage());
					}
				}
			}
		});
		btnView.setBounds(452, 1, 113, 27);
		frmBioToolkit.getContentPane().add(btnView);
	
		
		JButton btnCopyResults = new JButton("Copy Results");
		btnCopyResults.setHorizontalAlignment(SwingConstants.LEFT);
		btnCopyResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection clipstr=new StringSelection(textArea_1.getText());
				Clipboard clipbd= new Clipboard(textArea_1.getText());
				clipbd.setContents(clipstr, clipstr);
			}
		});
		btnCopyResults.setBounds(452, 174, 113, 27);
		frmBioToolkit.getContentPane().add(btnCopyResults);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int bufint,bufind;
				boolean found=false;
				String lines = "";
				if (textArea.getText().length()%20!=0) bufint=1;
				else bufint=0;
				str=textArea.getText();
				bufind=0;
				while(str.indexOf("GG")!=str.lastIndexOf("GG")&&str.indexOf("GG")!=0 &&str.length()>23){
					if(str.indexOf("GG")==str.indexOf("GGG")) {
						bufind+=str.indexOf("GGG");
						str=str.substring(str.indexOf("GGG"));
						found=true;
					}
					else {
						bufind+=(str.indexOf("GG")-1);
						str=str.substring(str.indexOf("GG")-1);
						found=true;
					}
					String strbuf=(str.substring(5, 10));
					if(strbuf.indexOf("TGG")!=-1) {
						ind=strbuf.indexOf("TGG")+5;
						Integer a=new Integer(ind);
						Integer b=new Integer(bufind);
						hiinds.add(a);
						bpinds.add(b);
						if(ind==5)str=str.substring(0,ind+2)+"A"+str.substring(ind+3);
						else str=str.substring(0,ind+1)+"A"+str.substring(ind+2);
						strs.add(str.substring(0, 23));
					}
					if(found=false) {
						str=str.substring(3);
						bufind+=3;
					}
					else {
						str=str.substring(23);
						bufind+=23;
					}
				}
				if(str.indexOf("GG")!=str.lastIndexOf("GG")&&str.indexOf("GG")!=0&&str.length()>11) {
					if(str.indexOf("GG")==str.indexOf("GGG")) {
						str=str.substring(str.indexOf("GGG"));
					}
					else {
						str=str.substring(str.indexOf("GG")-1);
					}
					if(str.substring(5,10).indexOf("TGG")!=-1) {
						ind=str.substring(5,10).indexOf("TGG")+5;
						Integer a=new Integer(ind);
						hiinds.add(a);
						if(ind==5)str=str.substring(0,ind+2)+"A"+str.substring(ind+3);
						else str=str.substring(0,ind+1)+"A"+str.substring(ind+2);
						strs.add(str);
					}
				}
				if(strs.size()>0) {
					try {
						lines+=textArea.getText()+"\n";
						for(int j=0;j<(strs.size()-1);j++) {
							lines+=bpinds.get(j).toString()+"bp: "+strs.get(j)+"\n";
						}
						lines+=bpinds.get(strs.size()-1).toString()+"bp: "+strs.get(strs.size()-1)+"\n";
					}
						catch (Exception e3){
							lblOrLoadFrom.setText(e3.getMessage());
						}
					textArea_1.setText(lines);
					Highlighter highLighter = textArea_1.getHighlighter();
					DefaultHighlighter.DefaultHighlightPainter y = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
					DefaultHighlighter.DefaultHighlightPainter b = new DefaultHighlighter.DefaultHighlightPainter(Color.MAGENTA);
					iter=bpinds.get(0).toString().length()+textArea.getText().length()+5+hiinds.get(0);
					for(int i=0;i<hiinds.size();i++) {
						try{
							if(i>0) {
								iter+=bpinds.get(i-1).toString().length()+23+hiinds.get(i);
								highLighter.addHighlight(iter,iter+3, y);
							}
							else {
								highLighter.addHighlight(iter,iter+3, y);
							}
								
						}
						catch (BadLocationException e2){
							lblOrLoadFrom.setText(e2.getMessage());
						}
					}
					for(int i=0;i<bpinds.size();i++) {
						try{
							highLighter.addHighlight(bpinds.get(i),bpinds.get(i)+hiinds.get(i), b);
							highLighter.addHighlight(bpinds.get(i)+hiinds.get(i),bpinds.get(i)+hiinds.get(i)+3, y);
							highLighter.addHighlight(bpinds.get(i)+hiinds.get(i)+3,bpinds.get(i)+23, b);
						}
						catch (BadLocationException e2){
							lblOrLoadFrom.setText(e2.getMessage());
						}
					}
				}
			}
		});
		btnRun.setBounds(389, 174, 57, 27);
		frmBioToolkit.getContentPane().add(btnRun);
		
		
		
	}
}
