package com.fiuba.diner.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.fiuba.diner.R;
import com.fiuba.diner.helper.ConnectionHelper;
import com.fiuba.diner.model.Category;
import com.fiuba.diner.model.Product;
import com.fiuba.diner.model.Subcategory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressLint("ValidFragment")
public class OpenTable extends ActionBarActivity implements OnClickListener {

	private static final String WEBAPP_HOST = "http://192.168.0.102:8080";

	private static final Gson gson = new Gson();

	private Map<String, List<String>> categorySubcategory;
	private Map<String, List<String>> subcategoryProduct;

	private List<String> categories;

	private List<String> subcategoriesToShow;
	private List<String> productsToShow;
	private Button addOrderButton;

	private String categoryPicked;
	private String subCategoryPicked;
	private String productPicked;

	private ArrayList<String> values;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		new GetCategoriesTask().execute();

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_open_table);

		if (savedInstanceState == null) {
			this.getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		// Instancio los mapas hardcodeados
		this.categorySubcategory = new HashMap<String, List<String>>();
		this.subcategoryProduct = new HashMap<String, List<String>>();

		this.categories = new ArrayList<String>();

		List<String> subCategories1 = new ArrayList<String>();
		List<String> subCategories2 = new ArrayList<String>();
		List<String> subCategories3 = new ArrayList<String>();
		List<String> subCategories4 = new ArrayList<String>();
		List<String> subCategories5 = new ArrayList<String>();
		List<String> subCategories6 = new ArrayList<String>();
		List<String> subCategories7 = new ArrayList<String>();

		List<String> products1 = new ArrayList<String>();
		List<String> products2 = new ArrayList<String>();
		List<String> products3 = new ArrayList<String>();
		List<String> products4 = new ArrayList<String>();
		List<String> products5 = new ArrayList<String>();
		List<String> products6 = new ArrayList<String>();
		List<String> products7 = new ArrayList<String>();
		List<String> products8 = new ArrayList<String>();
		List<String> products9 = new ArrayList<String>();
		List<String> products10 = new ArrayList<String>();
		List<String> products11 = new ArrayList<String>();
		List<String> products12 = new ArrayList<String>();
		List<String> products13 = new ArrayList<String>();
		List<String> products14 = new ArrayList<String>();
		List<String> products15 = new ArrayList<String>();
		List<String> products16 = new ArrayList<String>();
		List<String> products17 = new ArrayList<String>();
		List<String> products18 = new ArrayList<String>();

		// cargo rubros
		this.categories.add("ENTRADAS");
		this.categories.add("CARNES");
		this.categories.add("MINUTAS");
		this.categories.add("PASTAS CASERAS");
		this.categories.add("ACOMPAÑAMIENTOS");
		this.categories.add("POSTRES");
		this.categories.add("BEBIDAS");

		subCategories1.add("EMBUTIDOS");
		subCategories1.add("OTROS");
		this.categorySubcategory.put("ENTRADAS", subCategories1);

		// subCategories.clear();
		subCategories2.add("TERNERA");
		subCategories2.add("CERDO");
		subCategories2.add("POLLO");
		this.categorySubcategory.put("CARNES", subCategories2);

		// subCategories.clear();
		subCategories3.add("MILANESAS");
		this.categorySubcategory.put("MINUTAS", subCategories3);

		// subCategories.clear();
		subCategories4.add("TALLARINES AL HUEVO");
		subCategories4.add("RAVIOLES, ÑOQUIS DE PAPA, CANELONES DE");
		subCategories4.add("SORRENTINOS DE RICOTA Y JAMÓN");
		this.categorySubcategory.put("PASTAS CASERAS", subCategories4);

		// subCategories.clear();
		subCategories5.add("ENSALADAS Y GUARNICIONES");
		subCategories5.add("SANDWICHES");
		this.categorySubcategory.put("ACOMPAÑAMIENTOS", subCategories5);

		// subCategories.clear();
		subCategories6.add("CASEROS");
		subCategories6.add("HELADOS");
		this.categorySubcategory.put("POSTRES", subCategories6);

		// subCategories.clear();
		subCategories7.add("SIN ALCOHOL");
		subCategories7.add("CERVEZAS");
		subCategories7.add("ESPUMANTES");
		subCategories7.add("CAFETERIA");
		subCategories7.add("VINOS");
		this.categorySubcategory.put("BEBIDAS", subCategories7);

		// TODO cargo subcat prod
		products1.add("TABLA DE FIAMBRES $ 62.70");
		products1.add("FIAMBRE SURTIDO $ 39.60");
		products1.add("SALAME DE LA COLONIA $ 28.50");
		products1.add("JAMÓN CRUDO $ 28.50");
		this.subcategoryProduct.put("EMBUTIDOS", products1);

		// products.clear();
		products2.add("MATAMBRE ARROLLADO CASERO $ 29.60");
		products2.add("ARROLLADO DE POLLO CASERO $ 29.60");
		products2.add("PORCIÓN DE QUESO $ 18.60");
		products2.add("VITTEL TONNE $ 19.70");
		products2.add("LENGUA A LA VINAGRETA $ 17.50");
		products2.add("RABAS A LA ROMANA $ 54.00");
		products2.add("RABAS A LA PROVENZAL $ 59.50");
		products2.add("EMPANADAS CRIOLLAS $ 7.00");
		this.subcategoryProduct.put("OTROS", products2);

		// products.clear();
		products3.add("BIFE DE CHORIZO $ 52.90");
		products3.add("BIFE DE CHORIZO COMPLETO $ 65.90");
		products3.add("BIFE DE CHORIZO “LA PERLA” $ 65.90");
		products3.add("BIFE CHORIZO CON CHAMPIGNON $ 65.90");
		products3.add("BIFE DE CHORIZO A LA PIMIENTA $ 69.50");
		products3.add("MEDALLÓN DE LOMO $ 53.90");
		products3.add("MEDALLÓN DE LOMO COMPLETO $ 67.00");
		products3.add("MEDALLÓN DE LOMO “LA PERLA” $ 67.00");
		products3.add("MEDALLÓN DE LOMO C/ CHAMPIGNON $ 67.00");
		products3.add("MEDALLÓN DE LOMO A LA PIMIENTA $ 70.50");
		products3.add("ENTRECOTTE $ 47.50");
		products3.add("ENTRECOTTE COMPLETO $ 60.50");
		products3.add("ENTRECOTTE “LA PERLA” $ 60.50");
		products3.add("ENTRECOTTE CON CHAMPIGNON $ 60.50");
		products3.add("ENTRECOTTE A LA PIMIENTA $ 63.70");
		products3.add("CHURRASCO DE CUADRIL $ 32.00");
		this.subcategoryProduct.put("TERNERA", products3);

		// products.clear();
		products4.add("COSTELETITAS DE CERDO AL ANANÁ $ 48.50");
		products4.add("COSTELETITAS DE CERDO A LA RIOJANA $ 48.50");
		this.subcategoryProduct.put("CERDO", products4);

		// products.clear();
		products5.add("1/4 POLLO AL HORNO $ 29.60");
		products5.add("PECHUGA DESHUESADA GRILLE $ 31.80");
		products5.add("PECHUGA DESHUESADA AL CHAMPIGNON $ 47.50");
		products5.add("PECHUGA DESHUESADA AL ROQUEFORT $ 47.50");
		products5.add("PECHUGA DESHUESADA AL VERDEO $ 47.50");
		products5.add("SUPREMA DE POLLO $ 39.50");
		products5.add("SUPREMA NAPOLITANA $ 52.70");
		products5.add("SUPREMA AL CHAMPIGNON $ 52.70");
		products5.add("SUPREMA AL ROQUEFORT $ 52.70");
		products5.add("SUPREMA FUGAZZETTA $ 52.70");
		products5.add("SUPREMA AL ANANÁ $ 52.70");
		products5.add("SUPREMA MARYLAND $ 54.90");
		products5.add("SUPREMA “LA PERLA” $ 54.90");
		products5.add("1/2 POLLO DESHUESADO GRILLE $ 50.50");
		products5.add("1/2 DESHUESADO AL AJILLO $ 63.70");
		products5.add("1/2 DESHUESADO NAPOLITANO $ 63.70");
		products5.add("1/2 DESHUESADO AL CHAMPIGNON $ 63.70");
		products5.add("1/2 DESHUESADO AL ROQUEFORT $ 63.70");
		products5.add("1/2 DESHUESADO AL ANANÁ $ 63.70");
		products5.add("1/2 DESHUESADO AL VERDEO $ 63.70");
		this.subcategoryProduct.put("POLLO", products5);

		// products.clear();
		products6.add("MILANESA DE TERNERA $ 32.90");
		products6.add("MILANESA COMPLETA $ 47.20");
		products6.add("MILANESA NAPOLITANA $ 41.70");
		products6.add("MILANESA FUGAZZETTA $ 41.70");
		products6.add("MILANESA RELLENA C/JAMON Y QUESO $ 41.70");
		products6.add("MILANESA RELLENA CON ROQUEFORT $ 41.70");
		products6.add("MILANESA AL CHAMPIGNON $ 41.70");
		products6.add("MILANESA MEDITERRANEA $ 41.70");
		products6.add("MILANESA “LA PERLA” $ 52.70");
		this.subcategoryProduct.put("MILANESAS", products6);

		// products.clear();
		products7.add("Al Filetto $ 30.70");
		products7.add("A La Parisién $ 40.90");
		products7.add("A La Crema $ 35.00");
		products7.add("Gran Caruso $ 40.90");
		products7.add("Mixta $ 35.00");
		products7.add("Príncipe De Nápoles $ 40.90");
		products7.add("Blanca $ 35.00");
		products7.add("A los cuatro quesos $ 40.90");
		products7.add("Bolognesa $ 36.50");
		this.subcategoryProduct.put("TALLARINES AL HUEVO", products7);

		// products.clear();
		products8.add("Al Filetto $ 32.90");
		products8.add("A La Parisién $ 42.80");
		products8.add("A La Crema $ 37.50");
		products8.add("Gran Caruso $ 42.80");
		products8.add("Mixta $ 37.50");
		products8.add("Príncipe De Nápoles $ 42.80");
		products8.add("Blanca $ 37.50");
		products8.add("A los cuatro quesos $ 42.80");
		products8.add("Bolognesa $ 38.50");
		this.subcategoryProduct.put("RAVIOLES, ÑOQUIS DE PAPA, CANELONES DE", products8);

		// products.clear();
		products9.add("Al Filetto $ 36.50");
		products9.add("A La Parisién $ 46.00");
		products9.add("A La Crema $ 40.90");
		products9.add("Gran Caruso $ 46.00");
		products9.add("Mixta $ 40.90");
		products9.add("Príncipe De Nápoles $ 46.00");
		products9.add("Blanca $ 40.90");
		products9.add("A los cuatro quesos $ 46.00");
		products9.add("Bolognesa $ 41.90");
		this.subcategoryProduct.put("SORRENTINOS DE RICOTA Y JAMÓN", products9);

		// products.clear();
		products10.add("ENSALADA LA PERLA $ 28.90");
		products10.add("ENSALADA MIXTA ESPECIAL $ 18.90");
		products10.add("ENSALADA MIXTA $ 14.50");
		products10.add("ENSALADA RUSA $ 14.50");
		products10.add("ENSALADA DE TOMATE $ 14.50");
		products10.add("ENSALADA DE REMOLACHA $ 14.50");
		products10.add("ENSALADA DE ZANAHORIA $ 14.50");
		products10.add("ENSALADA DE ACHICORIA $ 14.50");
		products10.add("ENSALADA DE LECHUGA $ 14.50");
		products10.add("ENSALADA DE RUCULA CON QUESO PARMESSANO $ 20.90");
		products10.add("PANACHE DE VERDURAS $ 23.00");
		products10.add("PURÉ DE PAPAS $ 14.50");
		products10.add("PURÉ DE CALABAZA O MIXTO $ 14.50");
		products10.add("PAPAS A LA CREMA $ 19.90");
		products10.add("PAPAS FRITAS CON HUEVO REVUELTO $ 19.90");
		products10.add("REVUELTO GRAMAJO $ 24.00");
		products10.add("PAPAS FRITAS $ 12.50");
		products10.add("PAPAS REJILLA $ 13.90");
		products10.add("PAPAS NOISSETTE $ 16.00");
		this.subcategoryProduct.put("ENSALADAS Y GUARNICIONES", products10);

		// products.clear();
		products11.add("LOMITO COMPLETO $ 46.00");
		products11.add("LOMITO DE POLLO $ 46.00");
		this.subcategoryProduct.put("SANDWICHES", products11);

		// products.clear();
		products12.add("FLAN CASERO $ 14.50");
		products12.add("BUDÍN DE PAN CASERO $ 15.90");
		products12.add("ZINGARELLA CASERA $ 16.90");
		products12.add("ENSALADA DE FRUTAS $ 16.50");
		products12.add("ENSALADA DE FRUTAS CON BOCHA DE HELADO $ 20.90");
		products12.add("DURAZNOS EN ALMÍBAR $ 15.50");
		products12.add("ZAPALLO EN ALMÍBAR $ 15.50");
		products12.add("HIGOS EN ALMÍBAR $ 15.50");
		products12.add("BACLAWA $ 17.50");
		products12.add("STRUDEL  $ 18.60");
		products12.add("MOUSE DE CHOCOLATE $ 18.60");
		products12.add("BROWNIE CON HELADO DE CREMA $ 18.90");
		products12.add("TIRAMISU $ 20.90");
		products12.add("PORCIÓN CHANTILLÍ O DULCE/LECHE $ 6.50");
		this.subcategoryProduct.put("CASEROS", products12);

		// products.clear();
		products13.add("HELADO EN BOCHAS $ 15.50");
		products13.add("ALMENDRADO HELADO $ 15.50");
		products13.add("BOMBÓN SUIZO $ 15.50");
		products13.add("BOMBÓN ESCOCÉS $ 16.50");
		products13.add("CHARLOTTE  $ 19.90");
		products13.add("COPA HELADA “LA PERLA” $ 25.00");
		products13.add("DON PEDRO C/HELADO CREMA $ 26.50");
		this.subcategoryProduct.put("HELADOS", products13);

		// products.clear();
		products14.add("AGUA MINERAL GASIFICADA  $ 12.00");
		products14.add("AGUA MINERAL  $ 12.00");
		products14.add("GASEOSA CHICA  $ 12.00");
		products14.add("AGUA SABORIZADA  $ 13.50");
		products14.add("GASEOSA GRANDE  $ 25.00");
		products14.add("JUGO DE NARANJA $ 16.50");
		this.subcategoryProduct.put("SIN ALCOHOL", products14);

		// products.clear();
		products15.add("CERVEZA QUILMES BOCA ANCHA  $ 14.50");
		products15.add("CERVEZA QUILMES  $ 25.50");
		products15.add("CERVEZA IGUANA  $ 31.00");
		products15.add("CERVEZA ESTELA ARTOIS  $ 38.50");
		this.subcategoryProduct.put("CERVEZAS", products15);

		// products.clear();
		products16.add("CHAMPAGNE CHANDON EXTRA BRUT $ 108.90");
		products16.add("CHAMPAGNE CHANDON BABY  $ 33.00");
		products16.add("CHAMPAGNE MERCIER $ 60.50");
		products16.add("NEW AGE  $ 54.00");
		products16.add("WHISKY CHIVAS $ 45.00");
		products16.add("WHISKY BLENDERS $ 25.00");
		products16.add("FERNET BRANCA  $ 18.00");
		products16.add("CAMPARI  $ 30.00");
		products16.add("CINZANO $ 24.50");
		products16.add("GANCIA $ 24.50");
		this.subcategoryProduct.put("ESPUMANTES", products16);

		// products.clear();
		products17.add("CAFÉ O CORTADO $ 9.90");
		products17.add("CAFÉ DOBLE $ 13.90");
		products17.add("CAFÉ CON CREMA $ 13.90");
		products17.add("TE O TE DIGESTIVOS $ 9.90");
		this.subcategoryProduct.put("CAFETERIA", products17);

		// products.clear();
		products18.add("LUIGI BOSCA Malbec Reserva $ 154.00");
		products18.add("FINCA LA LINDA Cabernet Sauvignon $ 86.90");
		products18.add("FINCA LA LINDA Malbec $ 86.90");
		products18.add("FINCA LA LINDA Chardonnay $ 86.90");
		products18.add("CATENA ZAPATA D.V. Cabernet Malbec $ 132.00");
		products18.add("ALAMO Malbec $ 67.00");
		products18.add("ALAMO Cabernet $ 67.00");
		products18.add("UXMAL Cabernet $ 49.50");
		products18.add("UXMAL Malbec $ 49.50");
		products18.add("UXMAL Sauvignon Blanc $ 49.50");
		products18.add("ESTIBA I Cabernet $ 38.50");
		products18.add("ESTIBA I Malbec $ 38.50");
		products18.add("ALAMBRADO Malbec $ 69.90");
		products18.add("ALAMBRADO Cabernet Sauvignon $ 69.90");
		products18.add("FUZION ALTA Malbec $ 55.00");
		products18.add("FUZION ALTA Cabernet Sauvignon $ 55.00");
		products18.add("SANTA JULIA RESERVA Malbec $ 58.00");
		products18.add("SANTA JULIA RESERVA Tempranillo $ 58.00");
		products18.add("SANTA JULIA VARIETAL Malbec $ 31.00 $ 46.50");
		products18.add("SANTA JULIA VARIETAL Cabernet Sauvignon $ 46.50");
		products18.add("SANTA JULIA VARIETAL Syrah $ 46.50");
		products18.add("SANTA JULIA VARIETAL Chardonnay $ 46.50");
		products18.add("CONTE DE VALMONT Tinto $ 36.50 $ 52.90");
		products18.add("LATITUD 33 Malbec $ 61.50");
		products18.add("LATITUD 33 Cabernet Sauvignon $ 61.50");
		products18.add("CASTEL CHANDON . . . . . Blanco $ 42.90");
		products18.add("LOPEZ Tinto $ 30.90 $ 49.50");
		products18.add("LOPEZ Blanco $ 30.90 $ 49.50");
		products18.add("SAN FELIPE Tinto $ 26.50 $ 41.90");
		products18.add("SAN FELIPE Blanco $ 26.50 $ 41.90");
		products18.add("NORTON Clasico Tinto $ 24.90 $ 37.50");
		products18.add("NORTON Clasico Chardonnay $ 37.50");
		products18.add("COLON Borgoña O Chablis $ 28.50");
		products18.add("COLON Cabernet - Sauvignon $ 23.00 $ 36.50");
		products18.add("COLON Malbec $ 36.50");
		products18.add("COLON Chardonnay $ 36.50");
		products18.add("CHIAPPA Malbec Reserva $ 70.00");
		products18.add("CHIAPPA Cabernet Sauvignon Reserva $ 70.00");
		products18.add("CHIAPPA Malbec Clasico $ 48.50");
		products18.add("CHIAPPA Cabernet Sauvignon Clasico $ 48.50");
		products18.add("CHIAPPA Bonarda Bivarietal $ 20.90 $ 31.90");
		products18.add("ETCHART PRIVADO TORRONTÉS $ 35.50");
		products18.add("ESTANCIA MDZA. CHARDONNAY CHENIN $ 20.90 $ 27.50");
		products18.add("ESTANCIA MDZA. MERLOT - MALBEC $ 20.90 $ 27.50");
		products18.add("ESTANCIA MDZA Cabernet Malbec $ 27.50 ");
		products18.add("ESTANCIA MDZA Malbec $ 38.00 ");
		products18.add("ESTANCIA MDZA Cabernet $ 38.00 ");
		products18.add("ESTANCIA MDZA Syrah $ 38.00 ");
		products18.add("ESTANCIA MDZA Chardonnay $ 38.00");
		this.subcategoryProduct.put("VINOS", products18);

		this.addOrderButton = (Button) this.findViewById(R.id.add_product_button);
		this.addOrderButton.setOnClickListener(this);

		ListView listView = (ListView) this.findViewById(R.id.order_list);

		this.values = new ArrayList<String>();
		this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.values);

		listView.setAdapter(this.adapter);

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				OpenTable.this.values.remove(position);
				OpenTable.this.adapter.notifyDataSetChanged();
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		this.getMenuInflater().inflate(R.menu.open_table, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (this.addOrderButton.getId() == ((Button) v).getId()) {
			DialogFragment newFragment = new PickCategoryDialogFragment();
			newFragment.show(this.getSupportFragmentManager(), "PickCategoryDialogFragment");
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_open_table, container, false);
			return rootView;
		}
	}

	private class PickCategoryDialogFragment extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle(R.string.pick_category);
			CharSequence[] cs = OpenTable.this.categories.toArray(new CharSequence[OpenTable.this.categories.size()]);
			builder.setItems(cs, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					OpenTable.this.categoryPicked = OpenTable.this.categories.get(which);
					OpenTable.this.subcategoriesToShow = OpenTable.this.categorySubcategory.get(OpenTable.this.categories.get(which));
					DialogFragment newFragment = new PickSubCategoryDialogFragment();
					newFragment.show(OpenTable.this.getSupportFragmentManager(), "PickSubCategoryDialogFragment");
				}
			});
			return builder.create();
		}
	}

	private class PickSubCategoryDialogFragment extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle(R.string.pick_subcategory);
			CharSequence[] cs = OpenTable.this.subcategoriesToShow.toArray(new CharSequence[OpenTable.this.subcategoriesToShow.size()]);
			builder.setItems(cs, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					OpenTable.this.subCategoryPicked = OpenTable.this.subcategoriesToShow.get(which);
					OpenTable.this.productsToShow = OpenTable.this.subcategoryProduct.get(OpenTable.this.subcategoriesToShow.get(which));
					DialogFragment newFragment = new PickProductDialogFragment();
					newFragment.show(OpenTable.this.getSupportFragmentManager(), "PickProductDialogFragment");
				}
			});
			return builder.create();
		}
	}

	private class PickProductDialogFragment extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle(R.string.pick_product);
			CharSequence[] cs = OpenTable.this.productsToShow.toArray(new CharSequence[OpenTable.this.productsToShow.size()]);
			builder.setItems(cs, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item
					OpenTable.this.productPicked = OpenTable.this.productsToShow.get(which);
					OpenTable.this.values.add(OpenTable.this.categoryPicked + " - " + OpenTable.this.subCategoryPicked + " - " + OpenTable.this.productPicked);
					OpenTable.this.adapter.notifyDataSetChanged();
				}
			});
			return builder.create();
		}
	}

	private class GetCategoriesTask extends AsyncTask<String, Void, List<Category>> {

		private final ConnectionHelper connectionHelper = new ConnectionHelper();

		@Override
		protected List<Category> doInBackground(String... params) {
			List<Category> categories = null;
			try {
				String response = this.connectionHelper.get(WEBAPP_HOST + "/diner/getCategories.do");
				System.out.println(response);
				Type type = (new TypeToken<List<Category>>() {
				}).getType();
				categories = gson.fromJson(response, type);
				for (Object object : categories) {
					Category category = (Category) object;
					System.out.println(category.getDescription());
					for (Object obj2 : category.getSubcategories()) {
						Subcategory subcategory = (Subcategory) obj2;
						System.out.println(subcategory.getDescription());
						for (Object obj3 : subcategory.getProducts()) {
							Product product = (Product) obj3;
							System.out.println(product.getDescription());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return categories;
		}

	}

}
