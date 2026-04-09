package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private Marche marche;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	
	// Définition de la classe interne
	
	public class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for(int i=0; i<nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliseEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for(int i=0;i<etals.length;i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalsProd=0;
			for (int i=0; i<etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalsProd+=1;
				}			
			}
			Etal[] etalsProd = new Etal[nbEtalsProd];
			int indiceEtalsProd=0;
			for (int i=0; i<etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsProd[indiceEtalsProd] = etals[i];
					indiceEtalsProd++;
				}
			}
			return etalsProd;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			int nbEtalsLibres = 0;
			StringBuilder chaine = new StringBuilder();
			for(int i=0;i<etals.length;i++) {
				if(!etals[i].isEtalOccupe()) {
					nbEtalsLibres ++;
				}else {
					chaine.append(etals[i].afficherEtal() + "\n");
				}
			}
			chaine.append(("Il reste " + nbEtalsLibres + " étals non utilisés dans le marche.\n"));
			return chaine.toString();
		}
	}
	

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indice = marche.trouverEtalLibre();
		StringBuilder chaine = new StringBuilder();
		if(indice==-1) {
			chaine.append("Aucun étal n'est disponible !");
			return chaine.toString();
		}
		marche.utiliseEtal(indice, vendeur, produit, nbProduit);
		chaine.append("Le vendeur s'est installé à l'étal " + indice + ".\n");
		return chaine.toString();
	};
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		if (etals.length==0){
			chaine.append("Aucun vendeur ne propose de "+produit+" .\n");
			return chaine.toString();
		}else{
			chaine.append("Les vendeurs proposant des "+produit+" sont :\n");
			for(int i=0; i<etals.length; i++) {
				chaine.append(etals[i].getVendeur().getNom()+"\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		if (etal == null) {
			return "Ce vendeur n'existe pas";
		} else {
			return etal.libererEtal();
		}
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}
}