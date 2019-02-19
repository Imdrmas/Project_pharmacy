package org.imdrmas.entities;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Produits implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	
	private String designation;
	private int quantite;
	private double prix;
	
	public Produits(Long id, String designation, int quantite, double prix) {
		super();
		this.id = id;
		this.designation = designation;
		this.quantite = quantite;
		this.prix = prix;
	}
	public Produits() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
