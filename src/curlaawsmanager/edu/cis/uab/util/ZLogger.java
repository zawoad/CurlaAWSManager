package curlaawsmanager.edu.cis.uab.util;

import curlaawsmanager.edu.cis.uab.CurlaAWSManager;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class ZLogger {

	public static final Color ERROR_COLOR = new Color(229,96,93);
    public static final Color LOG_COLOR =  new Color(28,76,46);
    
    public static void msgOnDialog(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void d(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                appendToPane(CurlaAWSManager.tpController, message + "\n", LOG_COLOR);
            }
        });
    }

    public static void e(final String message) {
    
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
	        appendToPane(CurlaAWSManager.tpController, message + "\n", ERROR_COLOR);
            }
        });

    }

    public static void writeToFile(String text, String fileName) {
        BufferedWriter bw;
        try {
            File file = new File(fileName);
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public static void writeToFile(String text) {
        writeToFile(text, "/Users/shams/Desktop/locserverlog.log");
    }

    public static void sigMessage(final JTextPane tp, final String message, final Color c)
	{
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
	        int currentLength = tp.getDocument().getLength();

                appendToPane(tp, message + "\n", Color.BLACK);

                DefaultHighlighter.DefaultHighlightPainter highlightPainter
                        = new DefaultHighlighter.DefaultHighlightPainter(c);

                try {
                    tp.getHighlighter().addHighlight(currentLength, currentLength + message.length(),
                            highlightPainter);

                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });
		
	}
    
    
    public static void appendToPane(JTextPane tp, String msg, Color c) {
         StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        //aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
    

    public static void appendToPane(JTextPane tp, String msg) {
        appendToPane(tp, msg, Color.BLACK);
    }
}
