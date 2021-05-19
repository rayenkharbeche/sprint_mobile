/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities.gui.Chambre;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Category;
import com.mycompany.myapp.entities.Chambre;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.entities.services.ServiceCategory;
import com.mycompany.myapp.entities.services.ServiceChambre;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author amin
 */
public class AjoutChambre extends Form{
    
        public AjoutChambre(Form previous,User u) {
        setTitle("Ajouter une Chambre");
        setLayout(BoxLayout.y());
        
                ComboBox<String> comboCat = new ComboBox<>();
                
                ComboBox<String> comboEtat = new ComboBox<>();
                ComboBox<String> comboTrait = new ComboBox<>();
                
                comboEtat.addItem("Disponible");
                comboEtat.addItem("Non Disponible");
                
                comboTrait.addItem("Sterilisee");
                comboTrait.addItem("Non Sterilisee");
                
                
        ArrayList<Category> listA=ServiceCategory.getInstance().getAllCat();
        for(int i=0;i<listA.size();i++)
        {
            comboCat.addItem(listA.get(i).getNom());            
        }
        TextComponent tfnum= new TextComponent().label("Numero ");
        TextComponent tfnumetage= new TextComponent().label("Numero Etage ");
        TextComponent tfnbplace= new TextComponent().label("Nombre de place ");
        TextComponent tfservice= new TextComponent().label("Service");
        TextComponent tfbloc= new TextComponent().label("Bloc");
       
        Button btnValider = new Button("Ajouter Chambre");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfnum.getText().length()==0)||(tfnumetage.getText().length()==0)||(tfnbplace.getText().length()==0)||(tfservice.getText().length()==0)||(tfbloc.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Chambre e = new Chambre(Integer.valueOf(tfnum.getText()),Integer.valueOf(tfnumetage.getText()),Integer.valueOf(tfnbplace.getText()),tfservice.getText(),tfbloc.getText());
                        ArrayList <Category> catel = new ArrayList();
                        catel=ServiceCategory.getInstance().getbyCat(comboCat.getSelectedItem().toString());
                        for (Category a : catel)
                        {
                            e.setCategory_id(a.getId());
                        }
                        e.setEtat(comboEtat.getSelectedItem().toString());
                        e.setTraitement(comboTrait.getSelectedItem().toString());
                        
                        if( ServiceChambre.getInstance().addChambre(e))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfnum,tfnumetage,tfnbplace,tfservice,tfbloc,comboCat,comboEtat,comboTrait,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }

   
}
