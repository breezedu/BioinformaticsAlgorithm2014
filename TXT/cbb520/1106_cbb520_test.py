#include "Python.h"

####################################################################################################
#create an object of gene;
####################################################################################################
class gene:
    def __init__(self, name, sequence, length):
        self.n = name
        self.s = sequence
        self.l = length
    def getName(self):
        return self.n
    def setName(self, name):
        self.n = name
    def getSequence(self):
        return self.s
    def setSequence(self, sequence):
        self.s = sequence
    def getLength(self):
        return self.l
    def setLength(self, length):
        self.l = length



####################################################################################################
#create an object of contig:
####################################################################################################
class contig:
    def __init__(self, name, sequence, length):
        self.n = name
        self.s = sequence
        self.l = length
    def getName(self):
        return self.n
    def setName(self, name):
        self.n = name
    def getSequence(self):
        return self.s
    def setSequence(self, sequence):
        self.s = sequence
    def getLength(self):
        return self.l
    def setLength(self, length):
        self.l = length


####################################################################################################
#align each gene (from geneList) to whole contigs in contigList;
#whenever we got a fraction in some contig has >50% identical, we got a "Match"
####################################################################################################
def scoreContigList(geneList, contigList):
    numOfGenes = len(geneList)
    
    for i in range(1, numOfGenes, 1): #needs to be 1 to ignore the empty line in the beginning
        #print("i: " + str(i) + geneList[i].getSequence())
        print("contig number-------------------------" + str(numOfGenes))
        scoreContig(geneList[i], contigList)



####################################################################################################
#align a single gene with the whold contigs;
####################################################################################################
def scoreContig(gene, contigList):
    numOfContigs = len(contigList)
    score = 0
    print(str(numOfContigs)+ "---------------------------------------------------")
    #print("what does this gene say: " + gene.getSequence())
    j = 0
    while j < numOfContigs:
    #for j in range(0, numOfContigs, 1):
        currContig = contigList[j].getSequence();
        #print("-----------------------------------------------Current contig: " + currContig)
        
        if (compareTwoSeq(gene.getSequence(), currContig)):
            #print("----------------------------Score: " + str(score))
            score = score +1
            #print("----------------------------Score: " + str(score))
            #print("----------------------------------------j------------------------------" + str(j))
        j= j+1
        print("------------------Score to this contig: " + str(j) + " is " + str(score))
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

    #l = start;
    #print("l: -----------------" + str(l) + "end: " + str(end) + "contig length: " + str(contigLen) + "geneLength: " + str(geneLen)) 
    #for l in range(start, end, 1):
    while start < end:
        #print("start:" + str(start) + "end: " + str(end) )
        subSeqOfContig = contigSeq[start: start + geneLen]
        
        #print("subset result: " + subSeqOfContig + "---------------------------------")
        
        if(Over50Identical(subSeqOfContig, geneSeq)):
            print("---------------------------------out1------------------------------")
            start = start + 1
            return True      
        elif(Over50Identical(subSeqOfContig, revgeneSeq)):
            start = start + 1
            print("------------------------------------------------------out2------------")
            return True
        else:
            start = start + 1
            #l = l+1
            #print(str(l))
            #return False
            #print("-----False:)----------------------")
            #return False
    #print("---------------------:0------------:0-----------------------------------------") 
    return False



####################################################################################################
#check if two sequences have more than 50% identical blocks;
####################################################################################################
def Over50Identical(str1, str2):
    #print("str1: " + str1 + "str2: " + str2)
    Len = len(str1)
    #print("--------------------total: " + str(Len))
    
    ident = 0
    for m in range(0, Len, 1):
        #print("-----------ident start at 0?----------" + str(ident))
        #print("----------string compare-------" + str1[m] + str2[m])
        if(str1[m] == str2[m]):
            #print(str(ident))
            ident = ident + 1

    #print("--------------------------------------iden: " + str(ident) + " --length: " + str(Len/2))
    if(ident >= Len/2):
        return True
    else:
        return False
    
    #print("false")
    return False




####################################################################################################
#create contigArrayList method;
####################################################################################################
def createContigArrayList():

    print("Step 2: read contigs from abyss/velvet")
    new_contig = contig("", "", 0)
    contigList = []

    #add file routine here from resulting contig list (velvet/abyss)
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\abyss_k43_contigs.fa', 'r') as genesReader:
        lines = genesReader.read().splitlines()
        for n in range(0, len(lines) - 1 , 1):
            currStr = lines[n]
            if(len(currStr) > 0 and currStr[0] == '>'):
                new_contig.setLength(len(new_contig.getSequence()))
                if(new_contig.getLength() > 1000):
                    contigList.append(new_contig)
                new_contig = contig(currStr, "", 0)
            else:
                seq = new_contig.getSequence() + currStr
                new_contig.setSequence(seq)

    #printout the total number of contigs:            
    print("Contigs (>1000 bp): " + str(len(contigList)))

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
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\seqdump.txt', 'r') as genesReader:
        lines = genesReader.read().splitlines()
        for n in range(0, len(lines) - 1 , 1):
            currStr = lines[n]
            if(len(currStr) > 0 and currStr[0] == '>'):
                new_gene.setLength(len(new_gene.getSequence()))
                geneList.append(new_gene)
                new_gene = gene(currStr, "", 0)
            else:
                seq = new_gene.getSequence() + currStr
                new_gene.setSequence(seq)
    print("Genes: " + str(len(geneList)))

    #for p in range(0, len(geneList), 1):
    #    print("name: " + str(geneList[p].getName()) + " size: " + str(geneList[p].getLength()))
    return geneList



####################################################################################################
#create geneMain() method:
####################################################################################################
def main():
    geneList = createGeneArrayList()
    contigList = createContigArrayList()    
    scoreContigList(geneList, contigList)

if __name__ == "__main__":
    main()
        
    

            
            
        
        
        
        

    
    





