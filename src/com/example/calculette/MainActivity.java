package com.example.calculette;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener
{
	protected static final String DEFAULT_VALUE = "";
	
	protected EditText input;
	//Liste de boutons
	protected Integer[] buttons = {R.id.buttonSuppr,R.id.button0, R.id.Button1, R.id.button2, 
			R.id.button3,R.id.Button4,R.id.button5, R.id.button6, R.id.Button7, 
			R.id.button8,R.id.button9, R.id.buttonDiv, R.id.buttonEg,R.id.buttonMoins,
			R.id.buttonMult, R.id.buttonPlus, R.id.buttonPlus, R.id.ButtonPo} ;
	
	/*
	 * Liste des opérations
	 */
	protected List<Integer> tabCalcul;
	
	protected Boolean calculated = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tabCalcul = new ArrayList<Integer>();
        
        input = (EditText) findViewById(R.id.input);
        input.setText(DEFAULT_VALUE);
        
        for(int i=0;i < buttons.length;i++) {
        	findViewById(buttons[i]).setOnClickListener(this);
        }
        
    }


	@Override
	public void onClick(View v) {
		
		Integer viewId = v.getId();
		Button curButton = (Button) findViewById(viewId);
		
		//On efface si résultat calculé précédemment et qu'on entre un chiffre
		if(calculated && viewId != R.id.buttonSuppr && viewId != R.id.buttonDiv
				&& viewId != R.id.buttonMoins && viewId != R.id.buttonMult && viewId != R.id.buttonPlus) {
			tabCalcul.clear();
		}
		
		//on ajoute l'entrée à la table
		if(viewId != R.id.buttonSuppr && viewId != R.id.buttonEg && viewId != R.id.ButtonPo) {
			tabCalcul.add(viewId);
		}
		
		switch(v.getId()) {
		
			case R.id.buttonSuppr : clickSuppr();break;
			case R.id.buttonDiv : clickDiv();break;
			case R.id.buttonMoins : clickMoins();break;
			case R.id.buttonMult : clickMult();break;
			case R.id.buttonPlus : clickPlus();break;
			case R.id.buttonEg : clickEgal();break;
			
			default: 
				String stringVal = curButton.getText().toString();
				clickChiffre(stringVal);break;
		}
		
		//Pour effacer le résultat lorsque l'on click
		if(viewId != R.id.buttonEg) {
			calculated = false;
		}
		
	}

	/*
	 * Le calcul
	 */
	protected void calcul()
	{
		Double resultat = 0.00;
		String curValue = "";
		Double curValueDouble = 0.00;
		Integer lastOperator = 0;
		
		if(tabCalcul.size() > 0)
		{
			for (Integer val : tabCalcul) {
				Log.i("test", "==> "+((Button)findViewById(val)).getText().toString());
			}
			
			
			int i = 1;
			for (Integer val : tabCalcul) 
			{
				//Si chiffre
				if( val != R.id.buttonDiv && val !=  R.id.buttonMoins 
						&& val != R.id.buttonMult && val != R.id.buttonPlus) {
					curValue += ((Button) findViewById(val)).getText().toString();
				}
				
				//Si opérateur ou si fin
				if( i == tabCalcul.size() || val == R.id.buttonDiv || val ==  R.id.buttonMoins 
						|| val == R.id.buttonMult || val == R.id.buttonPlus)
				{
					Log.i("Entree courante", curValue);
					if(curValue != "") {
						curValueDouble = Double.valueOf(curValue);
					}
					
					//Si début
					if(lastOperator == 0) {
						resultat = curValueDouble;
					}
					else {
						if(lastOperator == R.id.buttonDiv) {
							resultat /= curValueDouble;
						}
						else if(lastOperator == R.id.buttonMoins) {
							resultat -= curValueDouble;
						}
						else if(lastOperator == R.id.buttonMult) {
							resultat *= curValueDouble;
						}
						else if(lastOperator == R.id.buttonPlus) {
							resultat += curValueDouble;
						}
						Log.i("Résultat", resultat.toString());
					}

					lastOperator = val;
					curValue = "";
					curValueDouble = 0.00;
				}
				
				++i;
			}
		}
		
		input.setText(resultat.toString());
		calculated = true;
	}
	
	/*
	 * 
	 */
	protected void clickSuppr()
	{
		input.setText(DEFAULT_VALUE);
		tabCalcul.clear();
		//tabCalcul.remove(tabCalcul.size()-1);
	}

	protected void clickChiffre(String chiffre)
	{
		//Log.i("chiffre", chiffre);
		if(!input.getText().equals(DEFAULT_VALUE) && !calculated) {
			chiffre = input.getText() + chiffre;
		}
		/*else if(calculated) {
			tabCalcul.clear();
			value = 0.00;
		}*/
		input.setText(chiffre);
	}
	
	/*
	 * 
	 */
	protected void clickDiv()
	{
		input.setText(input.getText().toString() + '/');
	}
	
	protected void clickEgal()
	{
		calcul();
	}
	
	protected void clickMoins()
	{
		input.setText(input.getText().toString() + '-');
	}
	
	protected void clickMult()
	{
		input.setText(input.getText().toString() + 'x');
	}
	
	protected void clickPlus()
	{
		input.setText(input.getText().toString() + '+');
	}
	
	
}
