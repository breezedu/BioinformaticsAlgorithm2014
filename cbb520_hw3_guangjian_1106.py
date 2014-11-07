#include "Python.h"
# * Taking your set of longest, conserved, unique gene sequences you found above, 
# * write a program to test the quality of an S. cerevisiae genome assembly.


####################################################################################################
#create an object of gene;
####################################################################################################
class gene:
    def __init__(self, name, sequence, length):
        self.name = name
        self.sequence = sequence
        self.length = length
        
    def getName(self):
        return self.name
    def setName(self, name):
        self.name = name
        
    def getSequence(self):
        return self.sequence
    def setSequence(self, sequence):
        self.sequence = sequence
        
    def getLength(self):
        return self.length
    def setLength(self, length):
        self.length = length



####################################################################################################
#create an object of contig:
####################################################################################################
class contig:
    def __init__(self, name, sequence, length):
        self.name = name
        self.sequence = sequence
        self.length = length
        
    def getName(self):
        return self.name
    def setName(self, name):
        self.name = name
        
    def getSequence(self):
        return self.sequence
    def setSequence(self, sequence):
        self.sequence = sequence
        
    def getLength(self):
        return self.length
    def setLength(self, length):
        self.length = length


####################################################################################################
#align each gene (from geneList) to whole contigs in contigList;
#whenever we got a fraction in some contig has >50% identical, we got a "Match"
####################################################################################################
def scoreContigList(geneList, contigList):

    print("Step 3: align genes to contigs:")
    
    numOfGenes = len(geneList)
    
    for i in range(1, numOfGenes, 1): #needs to be 1 to ignore the empty line in the beginning
        #print("i: " + str(i) + geneList[i].getSequence())
        print("There are: "  + str(numOfGenes) +"contigs: " )
        scoreContig(geneList[i], contigList)



####################################################################################################
#align a single gene with the whold contigs;
####################################################################################################
def scoreContig(gene, contigList):
    numOfContigs = len(contigList)
    score = 0
    print(str(numOfContigs)+ " ")
    #print("the sequence of current gene: " + gene.getSequence())
    
    j = 0
    while j < numOfContigs:
    #for j in range(0, numOfContigs, 1):
        currContig = contigList[j].getSequence();
        #print("Current contig: " + currContig.getName())
        
        if (compareTwoSeq(gene.getSequence(), currContig)):
            #print("Score: " + str(score))
            score = score +1
            #print("Score: " + str(score))
            #print("get a match at contig number: " + str(j))
        j= j+1
        print("Score to this contig: " + str(j) + " is " + str(score))
    print("Score: " + str(score) + " Gene: " + str(gene.getName()))
        




####################################################################################################
#compare two sequences:
    #the contig-sequence should be much longer than the gene sequence;
    #each time, cut-off a short sequence from the contig with the same length as the gene sequence;
    #call Over50Identical() method to check whether there's "match" or not;
####################################################################################################
def compareTwoSeq(geneSeq, contigSeq):
    contigLen = len(contigSeq)
    geneLen = len(geneSeq)
    start = 0
    end = contigLen - geneLen +1
    revgeneSeq = ""

    for k in range(0, geneLen):
        revgeneSeq = geneSeq[k] + revgeneSeq 

    #print("Start" + str(l) + "end: " + str(end) + "contig length: " + str(contigLen) + "geneLength: " + str(geneLen)) 
    #for or while loop: get a fraction for range(start, end, step 1):
    while start < end:
        #print("start:" + str(start) + "end: " + str(end) )
        subSeqOfContig = contigSeq[start: start + geneLen]
        
        #print("subset result: " + subSeqOfContig )

        ##
        ## Check both the original gene sequence and the reverse gene;
        ## if any get a match, return True;
        if(Over50Identical(subSeqOfContig, geneSeq)):
            print("Got a match for the original gene!")
            start = start + 1
            return True      
        elif(Over50Identical(subSeqOfContig, revgeneSeq)):
            start = start + 1
            print("Got a match for the reverse gene!")
            return True
        else:
            start = start + 1

            #return False
    #print("neither original nor reverse gene got a match, :( ") 
    return False



####################################################################################################
#check if two sequences have more than 50% identical blocks;
####################################################################################################
def Over50Identical(str1, str2):
    #if the length of contig is longer than the gene, just return false;
    if(len(str1)>len2)
        return False
    
    #print("str1: " + str1 + "str2: " + str2)
    Len = len(str1)
    #print("Length of the string: " + str(Len))
    
    ident = 0
    #check the count of identical nucletides from start to end;
    for m in range(0, Len, 1):
        if(str1[m] == str2[m]):
            ident = ident + 1

    #print("Matches: " + str(ident) + "total length: " + str(Len))
    #if there are more than 50% identical between two sequence, return True;
    if(ident >= Len/2):
        return True
    else:
        return False




####################################################################################################
#create contigArrayList method;
####################################################################################################
def createContigArrayList():

    print("Step 2: Read_In contigs from abyss/velvet output.fa documents:")
    new_contig = contig("", "", 0)
    contigList = []

    #add file routine here from resulting contig list (velvet/abyss)
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\abyss_k43_contigs.fa', 'r') as genesReader:
    #with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\abyss_k69_contigs.fa', 'r') as genesReader:
    #with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\velvet_contigs.fa', 'r') as genesReader:
        #readin data line by line
        lines = genesReader.read().splitlines()
        for n in range(0, len(lines) - 1 , 1):
            currStr = lines[n]
             #if the first char is '>', put curr contig object into ArrayList, then create a new contig object.
            if(len(currStr) > 0 and currStr[0] == '>'):
                new_contig.setLength(len(new_contig.getSequence()))
                if(new_contig.getLength() > 500):
                    contigList.append(new_contig)
                new_contig = contig(currStr, "", 0)
            #if the first char is not '>', just add another sequence to current contig's sequence
            else:
                seq = new_contig.getSequence() + currStr
                new_contig.setSequence(seq)

    #printout the total number of contigs:            
    print("Contigs (>500 bp): " + str(len(contigList)))

    #for p in range(0, len(contigList), 1):
    #    print("name: " + str(contigList[p].getName()) + " size: " + str(contigList[p].getLength()))
    return contigList




####################################################################################################
#create geneArrayList method:
####################################################################################################
def createGeneArrayList():

    print("Step 1: read genes")
    new_gene = gene("", "", 0)
    geneList = []

    #add file here from resulting Gene list (sequence.txt)
    #with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\seqdump_2.txt', 'r') as genesReader:
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\seqdump.txt', 'r') as genesReader:

        #readin data line by line
        lines = genesReader.read().splitlines()
        for n in range(0, len(lines) - 1 , 1):
            currStr = lines[n]
            #if the first char is '>', put curr gene object into ArrayList, then create a new gene object.
            if(len(currStr) > 0 and currStr[0] == '>'):
                new_gene.setLength(len(new_gene.getSequence()))
                geneList.append(new_gene)
                new_gene = gene(currStr, "", 0)
            #if the first char is not '>', just add another sequence to current gene's sequence;
            else:
                seq = new_gene.getSequence() + currStr
                new_gene.setSequence(seq)
                
    #printout the number of the genes in the arrayList            
    print("Genes: " + str(len(geneList)))

    #for p in range(0, len(geneList), 1):
    #    print("name: " + str(geneList[p].getName()) + " size: " + str(geneList[p].getLength()))
    return geneList



####################################################################################################
#create geneMain() method:
####################################################################################################
def main():

    #step 1: create an ArrayList to store all genes
    geneList = createGeneArrayList()

    #step 2: create an ArrayList to store all contigs
    contigList = createContigArrayList()

    #step 3: align each gene with all contigs, to check if there's any match for that gene;
    scoreContigList(geneList, contigList)

#check main
if __name__ == "__main__":
    main()
        
    

            
            
        
        
        
        

    
    





