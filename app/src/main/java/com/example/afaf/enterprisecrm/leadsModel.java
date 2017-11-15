package com.example.afaf.enterprisecrm;

/**
 * Created by enterprise on 11/10/16.
 */

public class leadsModel {
    private int id;
    private String leadId;
    private String AssignedToID;
    private String FirstName;
    private String LastName;
    private String CommertialName;
    private String Phone;
    private String Email;
    private String Comment;
    private String InterestedIn;
    private String Title;
    private String  LeadScore;
    private String LeadSource;
    private String LeadStage;
    private String CurrentAddress;
    private String Fax;
    private String LeadURL;
    private String  Annualrevenue;
    private String BusinessPartner;
    private String Profile;
    private String SalesAgent;
    private String RejectionReasons;
    private String NumOfEmployee;
    private String  LeadsNeeds;
    private String AssignedTo;
    private String Status;
    private String Position;
    private String ImplementStrategy;
    private String EvaluationTime;
    private String  ExpectedAnnualRevenu;
    private String  Em_Opcrm_Project_id;
    private String Em_Opcrm_Round_id;
    private String AttendPlace;
    private String Em_Opcrm_Current_Address;
    private String Em_Opcrm_fin_Pay_Id;
    private String Nationality;


    public leadsModel() {
    }

    public leadsModel(int id, String leadId, String assignedToID, String firstName, String lastName, String commertialName, String phone, String email, String comment, String interestedIn, String title, String leadScore, String leadSource, String leadStage, String currentAddress, String fax, String leadURL, String annualrevenue, String businessPartner, String profile, String salesAgent, String rejectionReasons, String numOfEmployee, String leadsNeeds, String assignedTo, String status, String position, String implementStrategy, String evaluationTime, String expectedAnnualRevenu, String em_Opcrm_Project_id, String em_Opcrm_Round_id, String attendPlace, String em_Opcrm_Current_Address, String em_Opcrm_fin_Pay_Id, String nationality) {
        this.id = id;
        this.leadId = leadId;
        AssignedToID = assignedToID;
        FirstName = firstName;
        LastName = lastName;
        CommertialName = commertialName;
        Phone = phone;
        Email = email;
        Comment = comment;
        InterestedIn = interestedIn;
        Title = title;
        LeadScore = leadScore;
        LeadSource = leadSource;
        LeadStage = leadStage;
        CurrentAddress = currentAddress;
        Fax = fax;
        LeadURL = leadURL;
        Annualrevenue = annualrevenue;
        BusinessPartner = businessPartner;
        Profile = profile;
        SalesAgent = salesAgent;
        RejectionReasons = rejectionReasons;
        NumOfEmployee = numOfEmployee;
        LeadsNeeds = leadsNeeds;
        AssignedTo = assignedTo;
        Status = status;
        Position = position;
        ImplementStrategy = implementStrategy;
        EvaluationTime = evaluationTime;
        ExpectedAnnualRevenu = expectedAnnualRevenu;
        Em_Opcrm_Project_id = em_Opcrm_Project_id;
        Em_Opcrm_Round_id = em_Opcrm_Round_id;
        AttendPlace = attendPlace;
        Em_Opcrm_Current_Address = em_Opcrm_Current_Address;
        Em_Opcrm_fin_Pay_Id = em_Opcrm_fin_Pay_Id;
        Nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getAssignedToID() {
        return AssignedToID;
    }

    public void setAssignedToID(String assignedToID) {
        AssignedToID = assignedToID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCommertialName() {
        return CommertialName;
    }

    public void setCommertialName(String commertialName) {
        CommertialName = commertialName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getInterestedIn() {
        return InterestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        InterestedIn = interestedIn;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLeadScore() {
        return LeadScore;
    }

    public void setLeadScore(String leadScore) {
        LeadScore = leadScore;
    }

    public String getLeadSource() {
        return LeadSource;
    }

    public void setLeadSource(String leadSource) {
        LeadSource = leadSource;
    }

    public String getLeadStage() {
        return LeadStage;
    }

    public void setLeadStage(String leadStage) {
        LeadStage = leadStage;
    }

    public String getCurrentAddress() {
        return CurrentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        CurrentAddress = currentAddress;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getLeadURL() {
        return LeadURL;
    }

    public void setLeadURL(String leadURL) {
        LeadURL = leadURL;
    }

    public String getAnnualrevenue() {
        return Annualrevenue;
    }

    public void setAnnualrevenue(String annualrevenue) {
        Annualrevenue = annualrevenue;
    }

    public String getBusinessPartner() {
        return BusinessPartner;
    }

    public void setBusinessPartner(String businessPartner) {
        BusinessPartner = businessPartner;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public String getSalesAgent() {
        return SalesAgent;
    }

    public void setSalesAgent(String salesAgent) {
        SalesAgent = salesAgent;
    }

    public String getRejectionReasons() {
        return RejectionReasons;
    }

    public void setRejectionReasons(String rejectionReasons) {
        RejectionReasons = rejectionReasons;
    }

    public String getNumOfEmployee() {
        return NumOfEmployee;
    }

    public void setNumOfEmployee(String numOfEmployee) {
        NumOfEmployee = numOfEmployee;
    }

    public String getLeadsNeeds() {
        return LeadsNeeds;
    }

    public void setLeadsNeeds(String leadsNeeds) {
        LeadsNeeds = leadsNeeds;
    }

    public String getAssignedTo() {
        return AssignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        AssignedTo = assignedTo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getImplementStrategy() {
        return ImplementStrategy;
    }

    public void setImplementStrategy(String implementStrategy) {
        ImplementStrategy = implementStrategy;
    }

    public String getEvaluationTime() {
        return EvaluationTime;
    }

    public void setEvaluationTime(String evaluationTime) {
        EvaluationTime = evaluationTime;
    }

    public String getExpectedAnnualRevenu() {
        return ExpectedAnnualRevenu;
    }

    public void setExpectedAnnualRevenu(String expectedAnnualRevenu) {
        ExpectedAnnualRevenu = expectedAnnualRevenu;
    }

    public String getEm_Opcrm_Project_id() {
        return Em_Opcrm_Project_id;
    }

    public void setEm_Opcrm_Project_id(String em_Opcrm_Project_id) {
        Em_Opcrm_Project_id = em_Opcrm_Project_id;
    }

    public String getEm_Opcrm_Round_id() {
        return Em_Opcrm_Round_id;
    }

    public void setEm_Opcrm_Round_id(String em_Opcrm_Round_id) {
        Em_Opcrm_Round_id = em_Opcrm_Round_id;
    }

    public String getAttendPlace() {
        return AttendPlace;
    }

    public void setAttendPlace(String attendPlace) {
        AttendPlace = attendPlace;
    }

    public String getEm_Opcrm_Current_Address() {
        return Em_Opcrm_Current_Address;
    }

    public void setEm_Opcrm_Current_Address(String em_Opcrm_Current_Address) {
        Em_Opcrm_Current_Address = em_Opcrm_Current_Address;
    }

    public String getEm_Opcrm_fin_Pay_Id() {
        return Em_Opcrm_fin_Pay_Id;
    }

    public void setEm_Opcrm_fin_Pay_Id(String em_Opcrm_fin_Pay_Id) {
        Em_Opcrm_fin_Pay_Id = em_Opcrm_fin_Pay_Id;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    @Override
    public String toString() {
        return "leadsModel{" +
                "id=" + id +
                ", leadId='" + leadId + '\'' +
                ", AssignedToID='" + AssignedToID + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", CommertialName='" + CommertialName + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", Comment='" + Comment + '\'' +
                ", InterestedIn='" + InterestedIn + '\'' +
                ", Title='" + Title + '\'' +
                ", LeadScore='" + LeadScore + '\'' +
                ", LeadSource='" + LeadSource + '\'' +
                ", LeadStage='" + LeadStage + '\'' +
                ", CurrentAddress='" + CurrentAddress + '\'' +
                ", Fax='" + Fax + '\'' +
                ", LeadURL='" + LeadURL + '\'' +
                ", Annualrevenue='" + Annualrevenue + '\'' +
                ", BusinessPartner='" + BusinessPartner + '\'' +
                ", Profile='" + Profile + '\'' +
                ", SalesAgent='" + SalesAgent + '\'' +
                ", RejectionReasons='" + RejectionReasons + '\'' +
                ", NumOfEmployee='" + NumOfEmployee + '\'' +
                ", LeadsNeeds='" + LeadsNeeds + '\'' +
                ", AssignedTo='" + AssignedTo + '\'' +
                ", Status='" + Status + '\'' +
                ", Position='" + Position + '\'' +
                ", ImplementStrategy='" + ImplementStrategy + '\'' +
                ", EvaluationTime='" + EvaluationTime + '\'' +
                ", ExpectedAnnualRevenu='" + ExpectedAnnualRevenu + '\'' +
                ", Em_Opcrm_Project_id='" + Em_Opcrm_Project_id + '\'' +
                ", Em_Opcrm_Round_id='" + Em_Opcrm_Round_id + '\'' +
                ", AttendPlace='" + AttendPlace + '\'' +
                ", Em_Opcrm_Current_Address='" + Em_Opcrm_Current_Address + '\'' +
                ", Em_Opcrm_fin_Pay_Id='" + Em_Opcrm_fin_Pay_Id + '\'' +
                ", Nationality='" + Nationality + '\'' +
                '}';
    }
}
