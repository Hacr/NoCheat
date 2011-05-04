package cc.co.evenprime.bukkit.nocheat.wizard.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.InputVerifier;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cc.co.evenprime.bukkit.nocheat.config.BooleanOption;
import cc.co.evenprime.bukkit.nocheat.config.ChildOption;
import cc.co.evenprime.bukkit.nocheat.config.LevelOption;
import cc.co.evenprime.bukkit.nocheat.config.TextFieldOption;

public class ChildOptionGuiFactory {

	public static JComponent create(ChildOption option) {

		if(option instanceof BooleanOption) {
			return createBoolean((BooleanOption)option);
		}
		else if(option instanceof TextFieldOption) {
			return createTextField((TextFieldOption)option);
		}
		else if(option instanceof LevelOption) {
			return createLogLevel((LevelOption)option);
		}

		throw new RuntimeException("Unknown ChildOption " + option);
	}

	private static JComboBox createLogLevel(final LevelOption option) {

		final JComboBox comboBox = new JComboBox();

		for(LevelOption.LogLevel o : LevelOption.LogLevel.values())
			comboBox.addItem(o);

		comboBox.setSelectedItem(option.getOptionValue());

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				option.setValue((LevelOption.LogLevel)comboBox.getSelectedItem());

			}

		});

		return comboBox;
	}

	private static JCheckBox createBoolean(final BooleanOption option) {

		final JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected(option.getBooleanValue());
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				option.setValue(checkBox.isSelected());

			}
		});

		return checkBox;
	}

	private static JTextField createTextField(final TextFieldOption option) {

		final JTextField textField = new JTextField(option.getValue());
		
		if(option.hasPreferredLength()) {
			textField.setColumns(option.getPreferredLength());
		}
		
		textField.setInputVerifier(new InputVerifier() {

			@Override
			public boolean verify(JComponent arg0) {

				if(option.setValue(textField.getText())){
					return true;
				}
				else {
					JOptionPane.showMessageDialog(textField, "Illegal value for this field");
					textField.setText(option.getValue());
					return false;
				}
			}
		});

		return textField;
	}
}