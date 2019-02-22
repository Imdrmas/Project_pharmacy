package org.imdrmas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imdrmas.dao.ProduitRepository;

import org.imdrmas.entities.Produits;
import org.imdrmas.service.Facture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.issamdrmas.connection.GetConnecation;
@Controller
public class ProduitController {
	@Autowired
    private ProduitRepository produitRepository;
	
	@RequestMapping(value=("/admin/index"), method=RequestMethod.GET)
	public String index(Model model, 
			@RequestParam(name="page",defaultValue="0")int p, 
			@RequestParam(name="size",defaultValue="6")int s,
			@RequestParam(name="mc",defaultValue="")String mc ) {
	
		Page<Produits> pageProduits = 
				produitRepository.chercher("%"+mc+"%", new PageRequest(p, s));
		
		model.addAttribute("listProduits",pageProduits.getContent());
		int[] pages=new int[pageProduits.getTotalPages()];
		model.addAttribute("pages",pages);
		model.addAttribute("size",s);
		model.addAttribute("pageCourante", p);
		model.addAttribute("mc", mc);

		return "produits";

}
	@RequestMapping(value="/admin/delete",method=RequestMethod.GET)
	public String delete(Long id,String mc, int page, int size) {
		produitRepository.delete(id);
		return "redirect:/admin/index?page="+page+"&size="+size+"&mc"+mc;
	}
	@RequestMapping(value="/admin/form",method=RequestMethod.GET)
	public String formProduit(Model model) {
		 model.addAttribute("produit",new Produits());
		return "formProduit";
	}
	@RequestMapping(value="/user/accueil",method=RequestMethod.GET)
	public String acheterProduit(Model model, 
			@RequestParam(name="page",defaultValue="0")int p, 
			@RequestParam(name="size",defaultValue="6")int s,
			@RequestParam(name="mc",defaultValue="")String mc ) {
	
		Page<Produits> pageProduits = 
				produitRepository.chercher("%"+mc+"%", new PageRequest(p, s));
		
		model.addAttribute("listProduits",pageProduits.getContent());
		int[] pages=new int[pageProduits.getTotalPages()];
		model.addAttribute("pages",pages);
		model.addAttribute("size",s);
		model.addAttribute("pageCourante", p);
		model.addAttribute("mc", mc);
		 return "accueil";
	}
	@RequestMapping(value="/admin/edit",method=RequestMethod.GET)
	public String editt(Model model, Long id) {
		Produits produit = produitRepository.findOne(id);
		model.addAttribute("produit",produit);
		return "editProduit";
	}
	@RequestMapping(value="/admin/save",method=RequestMethod.POST)
	public String save(Model model, Produits produit, BindingResult b) {
		if (b.hasErrors())
			return "formProduit";
		produitRepository.save(produit);
		return "confirmation";
	}
	@RequestMapping(value="/")
	public String home() {
		return "redirect:/user/accueil";
	}
	@RequestMapping(value="/404")
	public String accessDneied() {
		return "404";
	}
	@RequestMapping(value="/login")
	public String login() {
		return "login";
    }

	@RequestMapping(value="/user/panier",method=RequestMethod.GET)
	public String panier(Model model, Long id) {
		Produits produit = produitRepository.findOne(id);
		model.addAttribute("produits",produit);	

	    return "panier";
	}

	@RequestMapping(value="/user/AddPanier",method=RequestMethod.GET)
	public String doGetPanier(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		Produits produits = new Produits();
		Facture facutre = new Facture();
		List<Produits> liste = new ArrayList<Produits>();
		
		liste = GetConnecation.getAllProducts();
		
		String splitValues = request.getQueryString();
		Map<String, String> params = new HashMap<String, String>();
		String[] paramBrut = splitValues.split("&");

		for (String param : paramBrut) {
			String[] tab = param.split("=");
			String name = tab[0];
			if (tab.length > 1) {
				String value = tab[1];
				params.put(name, value);
			}
        }
		
		String[] produitsUrl = params.get("listeProduit").split("%0D%0A");

		for (String element : produitsUrl) {
			String[] produitQuantite = element.split("%3A");

			produits = liste.get(Integer.parseInt(produitQuantite[0]) -1);
			Integer quantite = Integer.parseInt(produitQuantite[1]);
			facutre.addPropduit(produits, quantite);
		}
		Map<Produits, Integer> listeProduits = facutre.getProduits();
             StringBuilder afficherFacture = facutre.afficherFacture(listeProduits);
			 request.setAttribute("afficherFacture", afficherFacture); 
        
       return "panier";	
	}
	
	@RequestMapping(value="/user/detait",method=RequestMethod.GET)
	public String detait(Model model, Long id) {
		Produits produit = produitRepository.findOne(id);
		model.addAttribute("produits",produit);
		return "detait";
	}


}
