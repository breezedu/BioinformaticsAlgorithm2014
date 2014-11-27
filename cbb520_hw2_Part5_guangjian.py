###########################################
# CBB520 HomeWork2 Part5
###########################################
# include "Python.h"
#
############################################################################
# Class definition for pilon-error (actually it is also SNP information)
# each object has name, sequence, and length parameters
############################################################################

class pilon:
    def __init__(self, name, sequence, length):
        self.name = name
        self.seuence = sequence
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


############################################################################
# Class definition for vcf-SNPs (get these information from *.vcf files
# each object has name, sequence, and length parameters
############################################################################

class snp:
    def __init__(self, chrom, position):
        self.chrom = chrom
        self.position = position
    def __eq__(self, other):
        return (self.chrom == other.getChrom() and self.position == other.getPos())
    
    def getChrom(self):
        return self.chrom
    def setChrom(self, chrom):
        self.chrom = chrom
        
    def getPos(self):
        return self.position
    def setPos(self, position):
        self.position = position
    


def createVcfChangeList():

    print("Step 1: make list of vcf changes ")

    snpList = []
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\contigs\\abyss_variants.txt', 'r') as genesReader:
        lines = genesReader.read().split('\n')

        count = 0
        for n in range(0, len(lines)-1, 1):
             
            currLine = lines[n]

            if currLine[0]!='#':
                count = count+1
               # print("n=" + str(n) + " line: " + lines[n])
                
                split = currLine.split('\t');
            
                currN = split[0]
                currP = split[1]
               # print("----------------------- >> Name: " + str(currN) + "   Position: "+ str(currP) + " count: " + str(count))
                new_snp = snp(currN, currP)
            
                snpList.append(new_snp)
            

        print("vcf-arrayList:" + str(len(snpList)))
        
    return snpList


###############################################################################
# create pilon SNPs arrayList
###############################################################################
def createPilonChangeList():

    print("Step 2: make a list of pilon changes ")
    
    snpList = []
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\contigs\\abyss1M_pilon.changes', 'r') as genesReader:
        lines = genesReader.read().split('\n')
        
        for n in range(0, len(lines)-1, 1):
            currLine = lines[n]

            split = currLine.split(':')
            currChrom = split[0]

            split2 = split[1].split(' ')
            currPos = split2[0]

            new_snp = snp(currChrom, currPos)
            snpList.append(new_snp)

        print("pilon-arrayList:" + str(len(snpList)))
        
    return snpList




##############################################################
# merge vcf-arrayList and pion-arrayList
##############################################################

def mergeLists(vcfList, pilonList):

    print("Step 3: meger vcf-list and pilon-list.")
    
    vSize = len(vcfList)
    pSize = len(pilonList)              

    merge = []
    count = 0
    for n in range(0, vSize, 1):
        currV = vcfList[n]
        for m in range(0, pSize, 1):
            currP = pilonList[m]
            if(currV == currP):
                count = count + 1
    print("duplicated: " + str(count) +" total: " + str(vSize + pSize - count))

    print("vcfList[0]: " + str(vcfList[0].getChrom()) + " pos:" +str(vcfList[0].getPos()))
    count = 0
    for n in range(0, vSize, 1):
        curr = vcfList[n]
        
        merge.append(curr)
        #    count = count + 1
        #    print("count: " + str(count) + " n:" + str(n) +" snp-chrom:" + curr.getChrom())           #wired, when n=1069, something wrong...
    #print("the size of merge-arrayList: " + str(len(merge))
    print("count: " + str(count))
    for m in range(0, pSize, 1):
        curr2 = pilonList[m]
        if curr2 not in merge:
            merge.append(curr2)
            count = count + 1;
    mS = len(merge)
    print(str(mS) + " count: " + str(count))

    return merge

        
###############################################################################
#method finds the SNPs in the pilon sequences compared to the contig sequences
#and outputs where on each contig the SNP is found
###############################################################################

def findSNP(str1, str2, name):
    #print("step 4: compare strings ")
    pLen = len(str1)
    cLen = len(str2)
    result = []
    
    #if (pLen == cLen):
    #   print("-----------------check------------------")
    if(pLen < cLen):
        total = pLen - 1
    else:
        total = cLen - 1
    for m in range(0, total, 1):
        #print (str1[m] + " : " + str2[m])
        if(str1[m] != str2[m]):
            currR = name + "___"+ str(m+1) + " : " + str1[m] + " " + str2[m]
            result.append(currR)
            print("---------------------any?---------------" + currR )

    return result




##########################################################################
#method compares a list of pilon sequences and a list of contig sequences
#and outputs where on each contig the SNP is found
##########################################################################

def compareList(pilonList, contigList):
    print("Step 3: compare list ")
    pNum = len(pilonList)
    cNum = len(contigList)
    result = []
    total = 0

    for i in range(0, pNum, 1):
        pName = pilonList[i].getName()
        pSeq = pilonList[i].getSequence()
        for j in range(0, cNum, 1):
            cName = contigList[j].getName()
            cSeq = contigList[j].getSequence()
            name = pilonList[i].getName() + " : " + contigList[j].getName()            
            if(pName == cName):
                result = findSNP(pSeq, cSeq, name)
                total = total + len(result)

    print("SNPs: " + total)
    return result    

    
#############################################################################
# main method
# calls the two methods to create two arrayLists of vcf-snp objects
# and pilon-error objects arrayList
# and compares the two arrays to find the total number of SNPs
#############################################################################

def main():

    #create an arrayList of SNPs from *.vcf file
    #these SNP information were generated by BWA and Samtools
    vcfList = createVcfChangeList()
    s1 = str(len(vcfList))


    #create an arrayList of Errors from *.change file
    #these SNP(error) information were generated by Pilon
    pilonList = createPilonChangeList()
    s2 = str(len(pilonList))


    #compare and merge the vcf-arrayList and pilon-arrayList, remove duplicates
    #after merging, the count of total SNPs are the ultimate number of SNPs
    merge = mergeLists(vcfList, pilonList)
    s3 = str(len(merge))
    print("vcf: " + s1 + "     " + "pilon: " + s2 + "     " + "total: " + s3)
    

####################################
# call main() function
####################################
if __name__ == "__main__":
    main()
    

