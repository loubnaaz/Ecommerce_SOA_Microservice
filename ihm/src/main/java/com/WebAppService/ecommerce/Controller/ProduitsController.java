package com.WebAppService.ecommerce.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.WebAppService.ecommerce.Model.Client;
import com.WebAppService.ecommerce.Model.Commande;
import com.WebAppService.ecommerce.Model.Produit;
import com.fasterxml.jackson.databind.ObjectMapper;/*
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;*/

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;




import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/produits") 

@SessionAttributes("commandes")
public class ProduitsController {
	
    private final RestTemplate restTemplate = new RestTemplate();

    private final String PRODUCT_SERVICE_URL = "http://localhost:8082/produits"; 

    List<Commande> commandes = new ArrayList<Commande>();
    
    // ✅ Fetch all products
    @GetMapping
    public String getProduits(Model model) {
        try {
            Produit[] produits = restTemplate.getForObject(PRODUCT_SERVICE_URL, Produit[].class);
            model.addAttribute("produits", List.of(produits));
        } catch (Exception e) {
            model.addAttribute("error", "Impossible de récupérer les produits.");
        }
        return "produits"; // Renders produits.html
    }
    

    @GetMapping("/{produitId}")
    public String showDetailProduct(@PathVariable int produitId, Model model) {
        try {
            // Fetch the product details
            String productApiUrl = PRODUCT_SERVICE_URL + "/" + produitId;
            Map<String, Object> produit = restTemplate.getForObject(productApiUrl, Map.class);

            // Fetch the list of all products
            String productsApiUrl = PRODUCT_SERVICE_URL; // Assuming this returns a list of all products
            List<Map<String, Object>> produits = restTemplate.exchange(
                    productsApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String, Object>>>() {}).getBody();

            // Add data to the model
            model.addAttribute("produit", produit);
            model.addAttribute("produits", produits); // Add all products to be displayed

        } catch (Exception e) {
            return "redirect:/produits?error=ProductNotFound";
        }

        return "DetailsProduit"; // Show product details + list of products
    }

    
    // ✅ Show the product creation form
    @GetMapping("/create")
    public String showCreateProductForm() {
        return "addProduit"; // Renders create_product.html
    }

    // ✅ Create a product with an image
    @PostMapping("/save")
    public String saveProduct(
            @RequestParam("type") String type,      
            @RequestParam("titre") String titre,
            @RequestParam("reference") int reference,
            @RequestParam("description") String description,
            @RequestParam("prix") int prix,
            @RequestParam("quantity") int quantity,
            @RequestParam("file") MultipartFile file) throws IOException {
    	 System.out.println("typeihm dd" + type);
        String apiUrl = PRODUCT_SERVICE_URL + "/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("type", type);
        body.add("titre", titre);
        body.add("reference", reference);
        body.add("description", description);
        body.add("prix", prix);
        System.out.println("typeihm" + type);
        body.add("quantity", quantity);
        
        body.add("file", file.getResource()); // Attach image

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        

        try {
            restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        } catch (Exception e) {
            return "redirect:/produits?error=CreationFailed";
        }

        return "redirect:/produits"; // Redirect to product list
    }

    // ✅ Show edit form
    @GetMapping("/edit/{produitId}")
    public String showEditProductForm(@PathVariable int produitId, Model model) {
        try {
            String apiUrl = PRODUCT_SERVICE_URL + "/" + produitId;
            Map<String, Object> produit = restTemplate.getForObject(apiUrl, Map.class);
            model.addAttribute("produit", produit);
        } catch (Exception e) {
            return "redirect:/produits?error=ProductNotFound";
        }
        return "editProduit"; // Renders edit_product.html
    }

    // ✅ Update a product
    @PostMapping("/update/{produitId}")
    public String updateProduct(
            @PathVariable int produitId,
            @RequestParam("type") String type,
            @RequestParam("titre") String titre,
            @RequestParam("reference") int reference,
            @RequestParam("description") String description,
            @RequestParam("prix") int prix,
            @RequestParam("quantity") int quantity) {

        String apiUrl = PRODUCT_SERVICE_URL + "/" + produitId;
        Map<String, Object> updatedProduct = Map.of(
            "type", type,
            "titre", titre,
            "reference", reference,
            "description", description,
            "prix", prix,
            "quantity", quantity 
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(updatedProduct, headers);

        try {
            restTemplate.exchange(apiUrl, HttpMethod.PUT, requestEntity, String.class);
        } catch (Exception e) {
            return "redirect:/produits?error=UpdateFailed";
        }

        return "redirect:/produits";
    }

    // ✅ Delete a product
    @GetMapping("/delete/{produitId}")
    public String deleteProduct(@PathVariable int produitId) {
        try {
            String apiUrl = PRODUCT_SERVICE_URL + "/" + produitId;
            restTemplate.delete(apiUrl);
        } catch (Exception e) {
            return "redirect:/produits?error=DeleteFailed";
        }
        return "redirect:/produits";
    }
    
    @Autowired
    private ObjectMapper objectMapper;

    
    @PostMapping("/add-to-cart/{produitId}")
    public String addToCart(@PathVariable int produitId,  @RequestParam("quantity") int quantity, Model model) {
        try {
            System.out.println("Fetching product from API: " + PRODUCT_SERVICE_URL); // Debugging

            String apiUrl = PRODUCT_SERVICE_URL + "/" + produitId;

            Produit produit = restTemplate.getForObject(apiUrl, Produit.class);
            
            Commande commande = new Commande();
            commande.setProduit(produit);
           System.out.println("quantity ="+quantity);
            // Vérifier si le produit est déjà dans le panier
            boolean exists = false;
            for (Commande item : commandes) {
                if (item.getProduit().getProduitId() == produit.getProduitId()) {
                	System.out.println("produit quantity " +item.getProduit().getQuantity());
                    item.setQuantity(quantity + item.getProduit().getQuantity());
                    commande.setPrixTotal(commande.getQuantity() * commande.getProduit().getPrix());
                    exists = true;
                    break;
                }
            }

            if (!exists) {
            	commande.setQuantity(quantity);
            	commande.setPrixTotal(commande.getQuantity() * commande.getProduit().getPrix());
                commandes.add(commande);
            }
            
            model.addAttribute("commandes", commandes);
            
        } catch (Exception e) {
            System.out.println("Erreur s'est produit");
        }
        return "redirect:/produits/cart";
    }


    // ✅ Display the cart
    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
    	
        List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("commandes");

        if (cart == null) {
            cart = new ArrayList<>();
        }
        //System.out.println("Cart content: " + cart);
        session.getAttributeNames().asIterator().forEachRemaining(attr -> 
            System.out.println("Session attribute: " + attr)
        );
        model.addAttribute("cart", cart);
        return "cart"; // Show cart page
    }

    // ✅ Clear the cart
    @GetMapping("/cart/clear")
    public String clearCart(Model model) {
    	commandes = new ArrayList<>();
    	model.addAttribute("commandes", commandes); // Remove cart from session
        return "redirect:/produits/cart";
    }
    

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
    	Object client = session.getAttribute("client"); // Vérifier si le client est connecté
    	System.out.println("=========== login !! =======");

        if (client == null) {
        	System.out.println("client n est pas connecté");
        	
            return "login"; // Redirige vers la page de connexion si l'utilisateur n'est pas authentifié
        } else {
        	System.out.println("client est connecté");
        }

        // Retrieve the list of commandes (orders) from the session
        List<Commande> commandes = (List<Commande>) session.getAttribute("commandes");
        if (commandes == null || commandes.isEmpty()) {
            model.addAttribute("errorMessage", "No products in the cart");
            return "checkout"; // Show an error message if the cart is empty
        }

        // Calculate the total price of the cart
        double totalInvoice = 0;
        for (Commande commande : commandes) {
            totalInvoice += commande.getPrixTotal();
        }

        // Add client, commandes, and total to the model
        model.addAttribute("client", client);
        model.addAttribute("commandes", commandes);
        model.addAttribute("totalInvoice", totalInvoice); // Total of the invoice
        model.addAttribute("client", client);
        
        
        return "checkout"; // Afficher la page checkout.html si connecté
    } 
    /*
    @GetMapping("/checkout") 
    public String checkout(HttpSession session, Model model) {
        // Vérifier si le client est connecté
        Client client = (Client) session.getAttribute("client"); 
        if (client == null) {
            System.out.println("Client non connecté, redirection vers login.");
            return "login"; // Redirige vers la page de connexion
        } 

        System.out.println("Client connecté: " + client.getNom());

        // Récupérer les produits du panier depuis la session
        List<Commande> cart = (List<Commande>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Calcul du total de la facture
        double totalInvoice = cart.stream()
                                  .mapToDouble(commande -> commande.getPrixTotal())
                                  .sum();

        // Ajouter les informations au modèle
        model.addAttribute("client", client);
        model.addAttribute("cart", cart);
        model.addAttribute("totalInvoice", totalInvoice);
        
System.out.println("totalInvoice" + totalInvoice);
        return "checkout"; // Afficher la page checkout.html avec les détails
    }
*/

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> generatePdf(HttpSession session) {
        try {
            // Retrieve the client from the session
            Client client = (Client) session.getAttribute("client");
            if (client == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            // Retrieve the list of commandes (assuming stored in session)
            List<Commande> commandes = (List<Commande>) session.getAttribute("commandes");
            if (commandes == null || commandes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Create the PDF document
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();

            // ✅ 1. Add Invoice Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Facture N° 123", titleFont);
            
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // ✅ 2. Add Client Information
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            document.add(new Paragraph("Informations Client:", boldFont));
            document.add(new Paragraph("Nom: " + client.getNom()));
            document.add(new Paragraph("Prenom: " + client.getPrenom()));
            document.add(new Paragraph("Email: " + client.getEmail()));
            document.add(new Paragraph("Adresse: " + client.getDescription()));
            document.add(new Paragraph("\n"));

            // ✅ 3. Add Order Details (Table)
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{3, 1, 2, 2});

            // Table headers
            table.addCell(new PdfPCell(new Phrase("Produit", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Quantité", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Prix unitaire", boldFont)));
            table.addCell(new PdfPCell(new Phrase("Prix Total", boldFont)));

            double total = 0;
            for (Commande commande : commandes) {
                table.addCell(commande.getProduit().getTitre());
                table.addCell(String.valueOf(commande.getQuantity()));
                table.addCell(String.format("%.2f €", commande.getProduit().getPrix()));
                table.addCell(String.format("%.2f €", commande.getPrixTotal()));
                total += commande.getPrixTotal();
            }
            document.add(table);

            // ✅ 4. Add Total Price
            document.add(new Paragraph("\n"));
            Paragraph totalParagraph = new Paragraph("Total de la facture: " + String.format("%.2f €", total), boldFont);
            totalParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParagraph);

            // Close the document
            document.close();

            // Prepare the response
            byte[] pdfBytes = out.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    } 

   /* @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> generatePdf() {
        try {
            // Création du document PDF
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();


            document.add(new Paragraph("Détail de la commande", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("\n"));

        
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Entêtes du tableau
            table.addCell("Produit");
            table.addCell("Quantité");
            table.addCell("Prix unitaire");
            table.addCell("Prix Total");

            // Remplissage du tableau avec les données
            for (Commande commande : commandes) {
            	System.out.println(commande);
            	System.out.println("=======");
                table.addCell(commande.getProduit().getTitre());
                table.addCell(String.valueOf(commande.getQuantity()));
                table.addCell(String.format("%.2f €", commande.getProduit().getPrix()));
                table.addCell(String.format("%.2f €", commande.getPrixTotal()));
            }

            // Ajout du tableau au document PDF
            document.add(table);
            document.close();

            // Préparer la réponse HTTP avec le fichier PDF
            byte[] pdfBytes = out.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mon-pdf.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }*/

}
