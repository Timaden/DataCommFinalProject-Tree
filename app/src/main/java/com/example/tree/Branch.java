package com.example.tree;

import java.util.Date;
import java.util.ArrayList;

public class Branch {


    private String branchName;
    private Leaf branchCreator;
    private int numberOfLeaves;
    private Date dateCreated = new Date();
    private ArrayList<Leaf> leaves;
    public void setNumberOfLeaves(int numberOfLeaves) {
        this.numberOfLeaves = numberOfLeaves;
    }



    public Branch(String branchName, Leaf branchCreator, int numberOfLeaves, Date dateCreated, ArrayList<Leaf> leaves) {
        this.branchName = branchName;
        this.branchCreator = branchCreator;
        this.numberOfLeaves = numberOfLeaves;
        this.dateCreated = dateCreated;
        this.leaves = leaves;
    }

    public Branch(){

    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Leaf getBranchCreator() {
        return branchCreator;
    }

    public void setBranchCreator(Leaf branchCreator) {
        this.branchCreator = branchCreator;
    }

    public int getNumberOfLeaves() {
        return leaves.size();
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ArrayList<Leaf> getLeaves() {
        return leaves;
    }

    public void setLeaves(ArrayList<Leaf> leaves) {
        this.leaves = leaves;
    }



}
