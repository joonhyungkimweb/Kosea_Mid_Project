import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

public class InsertTableFrame2 extends TableFrame2 {
//  �г�(����/�� �߰�/�� ����)
	JPanel p;
	JButton cfm;
	JButton add;
	JButton del;

//  �Է� ���̺�

	public InsertTableFrame2(String name) throws SQLException {
		super(name);

//		�ʱ�ȭ ����
		/* �гκ� �ʱ�ȭ */
		cfm = new JButton("�Է�");
		add = new JButton("�� �߰�");
		del = new JButton("�� ����");
		p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 5));
		/* ���̺� �� �ʱ�ȭ */
		table.setModel(insertModel);
		insertModel.addRow(defrow);

		if (name.equals("Products")) {
			setCellComboBox();
		}

//		�̺�Ʈ ����
		/* ���߰� ��ư */
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertModel.addRow(defrow);
			}
		});
		/* ����� ��ư */
		del.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getRowCount() > 0) {
					if (table.getSelectedRowCount() > 0) {
						while (table.getSelectedRowCount() > 0) {
							insertModel.removeRow(table.getSelectedRow());
						}
					} else {
						insertModel.removeRow(insertModel.getRowCount() - 1);
					}
				}
			}
		});

		/* �Է� ��ư */
		cfm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				result = JOptionPane.showConfirmDialog(sp, "�Է��Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					try {
						insert();
						insertModel.setNumRows(0);
						insertModel.addRow(defrow);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(table, e1.getMessage(), "����", 0);
					}
				}
			}
		});
	}

	public void initFrame() {
		/* �гκ� ��� */
		p.add(add);
		p.add(del);
		p.add(cfm);
		p.add(back);

		/* ������ ��� */
		f.add(sp, BorderLayout.CENTER);
		f.add(p, BorderLayout.NORTH);

		f.setVisible(true);
	}

//	insert ���� �ۼ�
	public void insert() throws Exception {
		InsertDAO2 dao = new InsertDAO2(this.name);

		while (table.getRowCount() > 0) {
			Vector<Object> data = new Vector<Object>();
			for (int i = 0; i < dao.column.size(); i++) {
				data.add(table.getValueAt(0, i));
			}
			try {
				dao.write(this.name, data);
				insertModel.removeRow(0);
			} catch (Exception e) {
				throw e;
			}
		}

	}



	public void setCellComboBox() {
		addCellComboBox(table.getColumnModel().getColumn(1),
				new String[] { "OUTER", "TOP", "BOTTOM", "ONEPIECE", "SHOES", "ACC", "SUMMER" });
		addCellComboBox(table.getColumnModel().getColumn(3), new String[] { "�Ǹ���", "ǰ��" });
		addCellComboBox(table.getColumnModel().getColumn(10), new String[] { "���Ǻ� ����", "����" });
	}

	public String toString() {
		return "Insert " + this.name;
	}

	public static void main(String[] args) throws SQLException {
		InsertTableFrame2 d = new InsertTableFrame2("Sales");
		d.initFrame();
	}

}