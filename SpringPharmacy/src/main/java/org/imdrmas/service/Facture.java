package org.imdrmas.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.imdrmas.entities.Produits;

public class Facture {
  private double totalPrix = 0;
  StringBuilder strF = new StringBuilder();
  StringBuilder strR = new StringBuilder();
  private Map<Produits, Integer> map = new HashMap<Produits, Integer>();
  
  public void addPropduit(Produits pro, int quantite) {
	  map.put(pro, quantite);
  }
  public Map<Produits, Integer> getProduits() {
		return map;
}
  
  public double getTotal(Map<Produits, Integer> map) {
	  for (Entry<Produits, Integer> element : this.map.entrySet()) {
		totalPrix += element.getKey().getPrix() * element.getValue();
	}
	  return totalPrix;
  }
  
  public StringBuilder afficherFacture(Map<Produits, Integer> produits) {
	    strF.append("------------------- FACTURE --------------------").append("<br> <br>");
	    strF.append("------------------- PRODUITS -------------------").append("<br><br>");
		strF.append(resumePanier(produits));
		strF.append("<br>-------------- MODE DE LIVRAISON ---------------").append("<br><br>");
		strF.append("<br>------------------------------------------------").append("<br><br>");
		strF.append("TOTAL : ").append(getTotal(map)).append("$").append("<br>");
	  return strF;
  }
  
  public StringBuilder resumePanier(Map<Produits, Integer> produits) {
	  for (Entry<Produits, Integer> element : this.map.entrySet()) {
			strR.append(element.getKey().getDesignation()).append(" - ").append(element.getKey().getPrix())
			.append("$ - ").append(element.getValue()).append(" unités <br>");
		}
	  return strR;
  }
}
