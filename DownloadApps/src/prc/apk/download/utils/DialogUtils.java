package prc.apk.download.utils;


import javax.swing.JOptionPane;

public final class DialogUtils {
	/**
	 * showErrorMsg
	 */
	public static final void showErrorMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * showConfirmMsg
	 */
	public static final int showConfirmMsg(String msg) {
	    return JOptionPane.showConfirmDialog(null, msg, "Confirm",JOptionPane.YES_NO_OPTION);  
	}
}
