# User interface Components
<table align="center">  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 1.png"/></td>  
    <td>UOI Creation Form:<br /> - Create button – when it is clicked Frontend send request message to the UOI Backend<br /> - Request message contains the filled UI fields.<br /> - Parent UOI is optional</td>  
  </tr>  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 2.png"/></td>  
    <td>Table of existing UOI nodes <br />- The table is populated with the created UOI after receiving response from Backend.</td>  
  </tr>  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 3.png"/></td>  
    <td>The "Properties" button when it is clicked (or when it is clicked over the row of the table) shows the "Properties" and "Update Parent UOI" components</td>  
  </tr>    
  <tr>  
    <td><img src="docs\images-readme\User interface Components 4.png"/></td>  
    <td>"Properties" component with metadata of the created UOI<br />- Add button - A new &ltkey, value&gt pair is prepared for the user to append to the UOI<br />- Update button – Takes all the new additions and updates the UOI (Frontend sends request message to the UOI Backend with the all &ltkey, value&gt pairs)</td>  
  </tr>  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 5.png"/></td>  
    <td>"Update Parent UOI" component<br />- For parent UOI must be provided an already existing UOI<br />- Clicking on "Update Parent UOI" button Frontend send request message to the UOI Backend with the valid UOI. Behind the scene relation between Parent UOI and early created UOI is made.<br />- Limitation: currently it cannot be delete relation when once is created</td>  
  </tr>
  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 9.png"/></td>  
    <td>"Hierarchy" <br />The Frontend shows parent and children only on one level up & down.</td>  
  </tr>
    
  <tr>  
    <td><img src="docs\images-readme\User interface Components 10.png"/></td>  
    <td>"Documents" <br />Shows integrated external to UOI services for documents to particular UOI</td>  
  </tr>
  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 6.png"/></td>  
    <td>"Search By UOI" <br />The UOI must be known in advance. I could be taken from "Table of existing UOI nodes"<br />Searching for UOI will give you the full information about the node if it exist.</td>  
  </tr>  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 7.png"/></td>  
    <td>"Search By Properties" <br /> The Properties must be known in advance. They could be taken from "Properties" component. The &ltkey, value&gt pair must be matched together with already stored &ltkey, value&gt pairs in Backend database in order to show at least one result.</td>  
  </tr>  
  <tr>  
    <td><img src="docs\images-readme\User interface Components 8.png"/></td>  
    <td>"Search Result Table" <br />  is populated when search results are found and it has have same structure as "Table of existing UOI nodes"</td>  
  </tr>  
</table>