package GUI;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import core.Plane;
import core.Vehicle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VehiclePlaneForm extends JPanel {
	JLabel contractorLabel,nameLabel;
	JComboBox<String> contractorDropDown;
	JTextField nameText;
	//private Vehicle t;
	private Plane p;
	public VehiclePlaneForm()
	{
		String[] contractor = {Vehicle.Contractors.DHL.toString(),Vehicle.Contractors.FedEX.toString(),Vehicle.Contractors.UPS.toString(),Vehicle.Contractors.USPS.toString()};
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("48px"),
				ColumnSpec.decode("69px"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("56px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		 contractorLabel = new JLabel();
		contractorLabel.setText("Contractor");
		add(contractorLabel, "2, 2, center, center");
		contractorDropDown = new JComboBox(Vehicle.Contractors.values());
		contractorDropDown.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String name = setName();
				if(nameText!=null)
					nameText.setText(name);
			}
		});
		contractorDropDown.setSelectedItem(Vehicle.Contractors.DHL);
		add(contractorDropDown, "4, 2, left, top");
		 nameLabel = new JLabel("Name");
		nameText = new JTextField(20);
		nameText.setText(setName());
		//nameText.setSize(69, Integer.parseInt(FormFactory.LINE_GAP_ROWSPEC.toString()));
		add(nameLabel,"2, 4, center,center");
		add(nameText,"4, 4, left, top");
	}
	private void loadVehicle(int id)
	{
		clearGUI();
		if(id>0)
		{
			p=Plane.Load(id);
			setPlane();
		}			
	}
	public void showPanel()
	{
		clearGUI();
		loadNew();
		this.setVisible(true);
	}
	public void showPanel(int id)
	{
		loadVehicle(id);
		this.setVisible(true);
	}
	public void hidePanel()
	{
		clearGUI();
		this.setVisible(false);
	}
	private void clearGUI()
	{
		this.contractorDropDown.setSelectedIndex(0);
		this.nameText.setText(setName());
	}
	private void loadNew()
	{

	}
	private void setPlane()
	{
		this.contractorDropDown.setSelectedItem(Vehicle.loadContractor(p.getContractor()));
		this.nameText.setText(p.getPlaneName());
	}
	private String setName(){
		int randomInt;
		boolean goodNumber = false;
		String name = "";
		ArrayList<Plane> planes = new ArrayList<Plane>();
		int trycounter=0;
		while(!goodNumber){
			randomInt = (int)Math.floor(Math.random()*10000);
			name = this.contractorDropDown.getSelectedItem().toString() + "Plane" + randomInt;
			planes = Plane.LoadAll("Where PlaneName ='" +name + "'");
			trycounter++;
			if(!(planes.size() > 0)){
				goodNumber = true;
			}
			else
			{
				if(trycounter==300)
				{
					name="NEWPLANE";
					goodNumber=true;
				}
			}
		}
		return name;
	}
}
