package br.com.corrida.util;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MensagemUtil {
	
	public static void mensagem(String mensagem, Integer tempoDaMensagem) {
		final JOptionPane mensagemEntrada = new JOptionPane(mensagem, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
		final JDialog modalEntrada = new JDialog();
		modalEntrada.setModal(true);
		modalEntrada.setContentPane(mensagemEntrada);
		modalEntrada.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		modalEntrada.pack();

		Timer timer = new Timer(tempoDaMensagem, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
		    public void actionPerformed(ActionEvent ae) {
				modalEntrada.dispose();
		    }
		});
		timer.setRepeats(false);
		timer.start();
		modalEntrada.setLocationRelativeTo(null);
		modalEntrada.setVisible(true);
		modalEntrada.toFront();
	}
}
