package org.imdrmas.controller;

import org.imdrmas.dao.ProduitRepository;
import org.imdrmas.entities.Produits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class ProduitController {
	@Autowired
    private ProduitRepository produitRepository;
	
	@RequestMapping(value=("/admin/index"), method=RequestMethod.GET)
	public String index(Model model, 
			@RequestParam(name="page",defaultValue="0")int p, 
			@RequestParam(name="size",defaultValue="5")int s,
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
		return "redirect:/user/index?page="+page+"&size="+size+"&mc"+mc;
	}
	@RequestMapping(value="/admin/form",method=RequestMethod.GET)
	public String formProduit(Model model) {
		 model.addAttribute("produit",new Produits());
		return "formProduit";
	}
	@RequestMapping(value="/user/accueil",method=RequestMethod.GET)
	public String acheterProduit(Model model, 
			@RequestParam(name="page",defaultValue="0")int p, 
			@RequestParam(name="size",defaultValue="5")int s,
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
		return "redirect:/admin/index";
	}
	@RequestMapping(value="/404")
	public String accessDneied() {
		return "404";
	}
	@RequestMapping(value="/login")
	public String login() {
		return "login";
    }
}
