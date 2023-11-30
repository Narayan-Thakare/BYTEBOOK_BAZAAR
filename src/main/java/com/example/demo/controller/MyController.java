package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Book;
import com.example.demo.model.ClientData;
import com.example.demo.model.Demand;
import com.example.demo.model.History;
import com.example.demo.model.Sellbooks;
import com.example.demo.pdfservice.DatabasePDFService;

@Controller
public class MyController {
	
	
	
	
	 @Autowired  
	  private  com.example.demo.repository.BookRepo stu;
	 
	 
	 @Autowired
	 private com.example.demo.repository.DemandRepo drp;
	 
	 @Autowired
	 private com.example.demo.repository.SellRepo slrp; 
	 
	 @Autowired
	 private  com.example.demo.repository.ClientRpo clrp;
	 
	 
	 
	 @Autowired
	 private com.example.demo.repository.HistoryRepo historyRepo;
	 
	 
	 
	 //////////////////////////////////////////////////////////////////////////////////////////
		
		
		
	 @GetMapping("/run")
	 public String yourMapping(Model model) {
	     Book s = new Book();
	     model.addAttribute("obj", s);
	     return "index";
	 }
	 	
	 	
	 	
	 	
	 	
	 	

	 	
	 	@RequestMapping("/login")
	 	public String loginPage()
	 	{
	 		return "login.html";
	 	}
	 	
	 	@RequestMapping("/logout-success")
	 	public String logoutPage()
	 	{
	 		return "logout.html";
	 	}
	 
	//////////////////////////////////////////////////////////////////////////////////////  
	    @RequestMapping("/")
	    String myfun(Model m) {
	        Book s = new Book();

	        System.out.println("Data in	 u object from " + s);
	        m.addAttribute("obj", s);

	        return "index.html";
	    }
	    
	    
	    
	    
	    @RequestMapping("/indexx")
	    String myfun1(Model m) {
	        Book s = new Book();

	        System.out.println("Data in	 u object from " + s);
	        m.addAttribute("obj", s);

	        return "add.html";
	    }
	    
	    
	    

	    @RequestMapping("/insert")
	    ModelAndView myfun2(@ModelAttribute("obj") Book s1 , History history) {
	        System.out.println("Data in u object from After " + s1);
	        
	        stu.save(s1);
	      

	        
	            historyRepo.save(history);
	     
	        
	        ModelAndView mv = new ModelAndView("index.html");
	        mv.addObject("ob", s1);

	        return mv; 
	    }
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    
	    
	    @RequestMapping("/show")
	    ModelAndView myfun246(@ModelAttribute("obj") Book s1) {
	        System.out.println("Data in u object from After " + s1);

	        // Assuming stu.findAll() returns a List of Students
	        List<Book> slist = stu.findAll();

	        ModelAndView mv = new ModelAndView("show.html");
	        mv.addObject("show", slist);

	        return mv; 
	    }
//////////////////////////////////////////////////////
	    
	    @RequestMapping("/showhistory")
	    ModelAndView myfun24(@ModelAttribute("obj") History s1) {
	        System.out.println("Data in u object from After " + s1);

	        // Assuming stu.findAll() returns a List of Students
	        List<History> slist = historyRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));

	        ModelAndView mv = new ModelAndView("showHistory.html");
	        mv.addObject("show", slist);

	        return mv; 
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    @GetMapping("/deletehistory/{id}")
	    public String myfun24(@PathVariable("id") Integer id) {

	    	historyRepo.deleteById(id);

	      
	        return "redirect:/showhistory";
	    }
	    
	    
	    
	    
	    @GetMapping("/deleteallhistory")
	    public String deleteAllHistory() {
	        historyRepo.deleteAll();
	        return "redirect:/showhistory";
	    }
	    
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////
	    
	

	    @GetMapping("/delete/{id}")
	    public String myfun4(@PathVariable("id") Integer id) {

	        stu.deleteById(id);

	      
	        return "redirect:/show";
	    }
	    
	    
	    /////////////////////////////////////////////////////
	    
	    


@RequestMapping("/update/{id}")
public ModelAndView showUpdateForm(@PathVariable("id") Integer id) {
   Optional<Book> book = stu.findById(id);

   ModelAndView mv = new ModelAndView("updatedata.html");
   mv.addObject("book", book.orElse(null)); // Pass the student to the update form
   return mv;
}


@PostMapping("/updatee")
public String updateStudent(@ModelAttribute Book updatedStudent) {
   stu.save(updatedStudent); // Save the updated student data
   return "redirect:/show"; // Redirect to the student list page after update
}

	    //////////////////////////////////////////





@RequestMapping("/addBook/{id}")
public ModelAndView showUpdateFormm(@PathVariable("id") Integer id) {
   Optional<Book> book = stu.findById(id);

   ModelAndView mv = new ModelAndView("addbooks.html");
   mv.addObject("book", book.orElse(null));
   mv.addObject("newQuantity", 0);
   // or any default value
   return mv;
}


@PostMapping("/addupdatebook")
public String updateBook(@ModelAttribute Book updateBook, @RequestParam("newQuantity") int newQuantity) {
   Optional<Book> optionalOldBook = stu.findById(updateBook.getId());

   if (optionalOldBook.isPresent()) {
       Book oldBook = optionalOldBook.get();

       // Add the new quantity to the old quantity
       int oldQuantity = oldBook.getQuantity();
       int totalQuantity = oldQuantity + newQuantity;

       // Set the updated quantity to the book
       updateBook.setQuantity(totalQuantity);

       // Save the updated book data
       stu.save(updateBook);
   }

   return "redirect:/show";
}












/////////////////////////////////////////////////////////

@RequestMapping("/search")
public ModelAndView searchBooks(@RequestParam("title") String title) {
   List<Book> searchResults = stu.findByTitleContaining(title);

   ModelAndView mv = new ModelAndView("searchdata.html");
   mv.addObject("searchob", searchResults);
   return mv;
}

////////////////////////////////////////////////////////////////////////////////////////////













//////////////////////////////////////////////////////////////////////

@RequestMapping("/outofstockk")
ModelAndView myfun244(@ModelAttribute("obj") Book s1) {
   System.out.println("Data in u object from After " + s1);

   // Assuming stu.findAll() returns a List of Students
   List<Book> slist = stu.findAllByQuantityLessThanEqual(5, Sort.by(Sort.Direction.DESC, "id"));

   ModelAndView mv = new ModelAndView("outofstock.html");
   mv.addObject("ofs", slist);

   return mv; 
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////


@RequestMapping("/demands")
String myfun178(Model m) {

   Demand d = new Demand();
   
   System.out.println("Data in	 u object from " + d);
   m.addAttribute("obj", d);

   return "addDemands.html";
}


@RequestMapping("/adddemand")
ModelAndView myfun25(@ModelAttribute("obj") Demand d1) {
   System.out.println("Data in u object from After " + d1);
   
   drp.save(d1);
   
   
   
   ModelAndView mv = new ModelAndView("index.html");
   mv.addObject("ob", d1);

   return mv; 
}


////////////////////////////////////////////////////////////////////////


@RequestMapping("/showdemands")
ModelAndView myfun242(@ModelAttribute("obj") Demand s1) {
   System.out.println("Data in u object from After " + s1);

   // Assuming stu.findAll() returns a List of Students
   List<Demand> slist = drp.findAll();

   ModelAndView mv = new ModelAndView("showDemands.html");
   mv.addObject("show", slist);

   return mv; 
}

///////////////////////////////////////////////////////////////////////////////////



@GetMapping("/deletedemands/{id}")
public String myfun43(@PathVariable("id") Integer id) {

   drp.deleteById(id);

 
   return "redirect:/showdemands";
}

////////////////////////////////////////////////////////////////////////////////////////////////


@RequestMapping("/updateDemands/{id}")
public ModelAndView showUpdateForm1(@PathVariable("id") Integer id) {
   Optional<Demand> demands = drp.findById(id);

   ModelAndView mv = new ModelAndView("updatedemandsdata.html");
   mv.addObject("demands", demands.orElse(null)); // Pass the student to the update form
   return mv;
}


@PostMapping("/updateDemands")
public String updateDemands(@ModelAttribute Demand updateDemands) {
   drp.save(updateDemands); // Save the updated student data
   return "redirect:/showdemands"; // Redirect to the student list page after update
}

	    /////////////////////////////////////////////////////////////////////////////////////////////////////////
	    


@RequestMapping("/sellBook/{id}")
public ModelAndView sellform(@PathVariable("id") Integer id) {
   Optional<Book> book = stu.findById(id);

   ModelAndView mv = new ModelAndView("sellbookdata.html");
   mv.addObject("book", book.orElse(null)); // Pass the student to the update form
   return mv;
}





@PostMapping("/sellupdate")
public String selldata(@ModelAttribute Sellbooks updateBook, @RequestParam("newQuantity") int newQuantity) {
   Optional<Book> optionalOldBook = stu.findById(updateBook.getId());

   if (optionalOldBook.isPresent()) {
       Book oldBook = optionalOldBook.get();

       // Add the new quantity to the old quantity
       int oldQuantity = oldBook.getQuantity();
       int updatedQuantity = oldQuantity - newQuantity;

       // Set the updated quantity to the book
       oldBook.setQuantity(updatedQuantity);
       
       // Save the updated quantity to the stu repository
       stu.save(oldBook);

       // Save the updated book data to slrp repository
       slrp.save(updateBook);
   }

   return "redirect:/show";
}



///////////////////////////////////////////////////////////////////////////////////////////////////////////




@RequestMapping("/generatedbill")
ModelAndView myfun245(@ModelAttribute("obj") Sellbooks s1) {
   System.out.println("Data in u object from After " + s1);

   // Assuming stu.findAll() returns a List of Students
   List<Sellbooks> slist = slrp.findAll();

   ModelAndView mv = new ModelAndView("bill.html");
   mv.addObject("show", slist);

   return mv; 
}










/*
* 
* @RequestMapping("/generatedata") String myfun11(Model m) { ClientData cd =
* new ClientData();
* 
* System.out.println("Data in	 u object from " + cd);
* m.addAttribute("obj",cd); slrp.gettatal("tatal",total);
* 
* return "Clientgeneratedbill.html"; }
*/
@RequestMapping("/generatedata")
public String generateData(Model model) {
   // Retrieve total and titles from SellRepo
   Double totalFromSellRepo = slrp.calculateTotal();
   List<String> titlesFromSellRepo = slrp.getTitles();

   // Generate ClientData
   ClientData cd = new ClientData();
   cd.setTotal(totalFromSellRepo);

   // Assuming you want to store all titles as a single string in ClientData
   // Modify this part based on your actual requirement
   String titlesAsString = String.join(", ", titlesFromSellRepo);
   cd.setTitle(titlesAsString);

   // Save the ClientData or perform other operations if needed
   clrp.save(cd);

   // Add the titles to the model
   model.addAttribute("titles", titlesFromSellRepo);

   // Add the ClientData object to the model
   model.addAttribute("clientData", cd);

   return "Clientgeneratedbill";
}






@RequestMapping("/clientdata")
public ModelAndView handleClientData(@ModelAttribute("obj") ClientData cl) {
   System.out.println("Data in u object from After " + cl);

   // Save the ClientData
   clrp.save(cl);

   // Assuming slrp is a repository for Sellbooks, delete all data
   slrp.deleteAll();

   ModelAndView mv = new ModelAndView("index.html");
   mv.addObject("ob", cl);

   return mv;
}

////////////////////////////////////////////////////////////////////////////////////////////

@RequestMapping("/clientshow")
ModelAndView myfun2436(@ModelAttribute("obj") ClientData s1) {
   System.out.println("Data in u object from After " + s1);

   // Assuming stu.findAll() returns a List of Students
//   List<ClientData> slist = clrp.findAll();
   List<ClientData> slist = clrp.findAll(Sort.by(Sort.Direction.DESC, "id"));

   ModelAndView mv = new ModelAndView("showclient.html");
   mv.addObject("show", slist);

   return mv; 
}


//////////////////////////////////////////////////////////////////////////////

/////              pdf

	
@GetMapping(value = "/openpdf/sellbook", produces = MediaType.APPLICATION_PDF_VALUE)
public ResponseEntity<InputStreamResource> employeeReport()  throws IOException {
    List<Sellbooks> Sellbooks = (List<Sellbooks>) slrp.findAll();

    ByteArrayInputStream bis = DatabasePDFService.employeePDFReport(Sellbooks);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=employees.pdf");

    return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
}
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	
}