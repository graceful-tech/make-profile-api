<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <style>
  
  @page {
     size: A4;
     margin: 30px 30px;
     }
	
   html, body {
       margin: 0;
	  padding: 0;
	  width: 210mm;
	  font-family: Arial, sans-serif;
	  font-size: 11pt;
	  color: #111;
	  box-sizing: border-box;
	  overflow-x: hidden;
	  word-break: break-word;
     }
	
    h1, h2 {
      color: #003366;
    }
    
    p{
       line-height:1.3;
       word-break: break-word;
       overflow-wrap: break-word;
    }
    
    h1 {
      font-size: 20px;
      text-align: center;
      margin-bottom: 5px;
    }
    
    h2 {
      font-size: 16px;
      border-bottom: 1px solid #003366;
      margin-top: 25px;
	  justify-content:center;
    }
    
    .contact {
      text-align: center;
      font-size: 13px;
      margin-bottom: 20px;
    }
    .section {
      margin-top: 15px;
    }
    
    .job-title {
      font-weight: bold;
    }
    
    .job-details {
      margin: 5px 0 10px 0;
      line-height: 1.5;
    }
    
    .edu-entry, .strengths, .skills-list, .achievements-list, .org-skills {
      margin-bottom: 10px;
    }
    
    ul {
      padding-left: 18px;
      margin-top: 5px;
      margin-bottom: 5px;
    }
    
	li{
	padding-bottom:3px;
	line-height:1.3;
	}
	
    .declaration {
      margin-top: 20px;
      font-style: italic;
    }
    .two-col {
      display: flex;
      justify-content: space-between;
    }
    .column {
      width: 48%;
    }
    hr {
      border: none;
      border-top: 2px solid #003366;
      margin: 20px 0;
    }
	
	.section {
      margin-top: 13px;
      margin-bottom: 0px;
     }

     .section-title {
	 font-weight: bold;
	  margin-bottom: 5px;
	  padding-bottom: 3px;
	  font-size: 16px;
	  border-bottom: 1px solid #003366;
	  color: #003366;
	  text-align: center;
	   
     }

	 .education{
	   padding-bottom: 3px;
	 }
	 
	 .percentage{
	   font-weight: bold;
	 }
	 
	 .EducationYear{
	   font-weight: bold; 
	 }
 
	 .container {
	      max-width: 190mm;
		  margin: auto;
		  padding: 10px 15px;
		  box-sizing: border-box;
	  }

  </style>
</head>
<body>

<#function extractYear input>
  <#if input?is_date>
    <#return input?string("yyyy")>
  <#elseif input?? && input?has_content>
    <#-- Try parsing known formats -->
    <#attempt>
      <#-- Try dd/MM/yyyy -->
      <#local parsedDate = input?date("dd/MM/yyyy")>
      <#return parsedDate?string("yyyy")>
    <#recover>
      <#attempt>
        <#-- Try yyyy-MM-dd -->
        <#local parsedDate = input?date("yyyy-MM-dd")>
        <#return parsedDate?string("yyyy")>
      <#recover>
        <#-- Return blank if parsing fails -->
        <#return "">
      </#attempt>
    </#recover>
  <#else>
    <#return "">
  </#if>
</#function>


   
    <div class="container">  
      <h1>${name}</h1>
      
     <div class="contact">
     
       <#if email?has_content>
          <strong>Mail: </strong> ${email} 
        </#if>
        
        <#if phone?has_content>
          | <strong>Mobile: </strong> ${phone}
         </#if> 
         
         <#if linkedin?has_content> 
          | <strong>LinkedIn: </strong> ${linkedin}
          </#if> 
          
      </div>

       <#if objective?has_content>
          <div class="section">
                <div class="section-title">Carrier Objectives</div>
                 <p>${objective}</p>
           </div>  
        </#if> 

       <#if summary?has_content>
		   <div class="section">
			    <div class="section-title">Summary</div>
			     <p>${summary}</p>
			</div>  
       </#if>
    
       <#if experiences?? && experiences?size gt 0> 
			 <div class="section">
			     <div class="section-title">Professional Experience</div>
			             
			           <#list experiences as experience>
			                
			                <#if experience.companyName?has_content> 
			         		   <div class="job-title">Company: ${experience.companyName} 
			         		   
			         		   <#if experience.experienceYearStartDate?? && experience.experienceYearStartDate?has_content> 
				         		    <span class="EducationYear">
				         		       ( ${extractYear(experience.experienceYearStartDate)} 
				         		       <#if experience.experienceYearEndDate?? && experience.experienceYearEndDate?has_content>
								          - ${extractYear(experience.experienceYearEndDate)} )
								           <#else>
								             - Present )
								       </#if>
				         		    </span>
			         		   </#if>
			         		    </div>
			                 </#if>
			                 
			                <#if experience.role?has_content || experience.responsibilities?trim?length gt 0>
			           			<div class="job-details">
			             		   <#if experience.role?has_content> 	 
			             			 <strong>Role: ${experience.role}</strong><br/>
			             			</#if> 
			             			    <#if experience.responsibilities?? && experience.responsibilities?trim?length gt 0>
									            <ul>
								                     <#list experience.responsibilities?split(",") as item>
												            <#if item?has_content>
												              <li>${item?trim}</li>
												            </#if>
												      </#list>
 									            </ul>
									    </#if>           
			            		</div>
			                 </#if>		
			            </#list>   		
			     </div>  
         </#if>
         
         
             <#if collegeProject?? && collegeProject?size gt 0> 
			 <div class="section">
			     <div class="section-title">Academic Project</div>
			             
			           <#list collegeProject as project>
			                
			                <#if project.collegeProjectName?has_content> 
			         		   <div class="job-title">Title: ${project.collegeProjectName} 
 			         		    
			         		    </div>
			                 </#if>
			                <#if project.collegeProjectSkills?has_content >
			           			<div class="job-details"> 
			             			    <#if project.collegeProjectSkills?? && project.collegeProjectSkills?trim?length gt 0>
			             			    <strong>Skills:</strong> <br/>
									            <ul>
								                     <#list project.collegeProjectSkills?split(",") as item>
												            <#if item?has_content>
												              <li>${item?trim}</li>
												            </#if>
												      </#list>
 									            </ul>
									    </#if>           
			            		</div>
			                 </#if>	
			                 
			                 <if project.collegeProjectDescription?has_content >
			                        <div class="job-details">
			                        <strong>Description:</strong> <br/>
			                            <p> ${project.collegeProjectDescription}</p>
			                        <div>
			                 
			                 </#if>	
			            </#list>   		
			     </div>  
         </#if>
         
         
	          
	          <#assign hasProjects = false>
						<#if experiences?? && experiences?size gt 0>
						    <#list experiences as exp>
						       <#if exp.projects?? && exp.projects?size gt 0>
						          <#assign hasProjects = true>
						        <#break>
						    </#if>
						  </#list>
						</#if>
 
		   <#if hasProjects>	 
				 <div class="section">
				        <div class="section-title">Project</div>
				            <#list experiences as exp> 
					           <#if exp.projects?? && exp.projects?size gt 0>
					              <#list exp.projects as proj>
				                       <#if proj?? && proj.projectName?has_content>
				                           <div class="job-title"><strong>Project Name:</strong> ${proj.projectName}</div>
				                        </#if>   
				                        
				                         <#if proj?? && proj.projectRole?has_content> 
 				                                  <div class="job-details">
				                                       <strong>Project Role:</strong> ${proj.projectRole}<br/>
				                                       <#if proj?? && proj.projectDescription?has_content> 
					                                        <p><strong>Project Description:</strong> ${proj.projectDescription}</p>
					                                    </#if>     
				                                   </div>
				                                   
				                          </#if>          
				                   </#list>
				               </#if> 
				            </#list>      
				 </div>           
		    </#if>		 

           <#if education?? && education?size gt 0> 
			  <div class="section">
			      <div class="section-title">Education</div>
			           <div class="edu-entry">
			               <#list education as edu>
			                      <#if edu.department?has_content || edu.institutionName?has_content> 
 			                            <div class="education"> 
			                               <#if edu.institutionName?has_content>
			                                 ${edu.institutionName}
			                                </#if>  
			                                <#if edu.department?has_content>  
			                                  - ${edu.department}
			                                 </#if> 
			                                 <#if edu.percentage?has_content>
			                                  <span class="percentage"> - ${edu.percentage}%</span> 
			                                 </#if>  
			                                 <#if edu.qualificationStartYear?? && edu.qualificationStartYear?has_content> 
			                                   <span class="EducationYear">( ${extractYear(edu.qualificationStartYear)} 
			                                      <#if edu.qualificationEndYear?? && edu.qualificationEndYear?has_content>
                                                    - ${extractYear(edu.qualificationEndYear)} )
                                                      <#else>
                                                  - Present )</#if></span>
			                                 </#if>      
			                            </div>
			                        </#if>
 			                </#list>  
 			            </div>
			     </div>  
			  </#if>
			  
			  
			  <#if skills?? && skills?trim?length gt 0> 
		        <div class="section">
		              <div class="section-title">Skills</div>
					          <ul class="skills-list">
					             <#list skills?split(",") as skill>
					                   <#if skill?? && skill?has_content>
					                      <li>${skill?trim}</li>
					                    </#if> 
					           </#list>
					       </ul>
					       
		        </div>
		    </#if> 



			<#if achievements?? && achievements?size gt 0> 
				 <div class="section">
				      <div class="section-title">Achievements</div>
				        <div class="achievements-list">
						    <ul>
						      <#list achievements as achieve>
						         <#if achieve.achievementsName?? && achieve.achievementsName?has_content>
						            <li>${achieve.achievementsName} 
						                <#if achieve.achievementsDate?? && achieve.achievementsDate?has_content>
						                  <span class="EducationYear">${extractYear(achieve.achievementsDate)} </span>
						                </#if>  
						            </li>
						         </#if> 
 						      </#list> 
						    </ul>
				       </div>
				 </div>
		    </#if>
		    
		    <#if certificates?? && certificates?size gt 0> 
				 <div class="section">
				      <div class="section-title">Certificates</div>
				        <div class="achievements-list">
						    <ul>
						      <#list certificates as certi>
						         <#if certi.courseName?has_content>
						            <li>${certi.courseName} 
						                <#if certi.courseStartDate?? && certi.courseStartDate?has_content>
						                   <span class="EducationYear"> ( ${extractYear(certi.courseStartDate)} 
						                  
								                   <#if  certi.courseEndDate?? && certi.courseEndDate?has_content>
		                                                    - ${extractYear(certi.courseEndDate)} )
		                                             </#if>
                                            </span>
						                </#if>  
						            </li>
						         </#if> 
 						      </#list> 
						    </ul>
				       </div>
				 </div>
		    </#if>	
		    
		    <#if competencies?? && competencies?trim?length gt 0> 
		        <div class="section">
		              <div class="section-title">Core Competencies</div>
					          <ul class="skills-list">
					             <#list competencies?split(",") as comp>
					                   <#if comp?? && comp?has_content>
					                      <li>${comp?trim}</li>
					                    </#if> 
					           </#list>
					       </ul>
					       
		        </div>
		    </#if>
		    
		    <#if softSkills?? && softSkills?trim?length gt 0> 
		        <div class="section">
		              <div class="section-title">Soft Skills</div>
					          <ul class="skills-list">
					             <#list softSkills?split(",") as skill>
					                   <#if skill?? && skill?has_content>
					                      <li>${skill?trim}</li>
					                    </#if> 
					           </#list>
					       </ul>
					       
		        </div>
		    </#if>  

   
			<div class="section">
				  <h2>Declaration</h2>
				  <p class="declaration">
				    I hereby declare that the above information given in this is true to my knowledge and belief.
				  </p>
			</div>  
  
    
  
</div>
</body>
</html>
