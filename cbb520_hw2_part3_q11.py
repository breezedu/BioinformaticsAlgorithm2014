######################
#build BWA and Samtools inside a Python script
#import subprocess and system os

import subprocess as sp
import os, sys


########################################
# create a bedLine object
# because after the *.sai -> *.sam -> *_sorted.sam -> *.BED process
# we would get a BED file, from which each line represents one pair-end read
# and it's alignment to the reference genome;
# hence, we create bedLine object, to record the chromosome location, the start position
# the end position, and the name of that pair-end read.

class bedLine:
    def __init__(self, name, start, end, index, frame):
        self.name = name
        self.start = start
        self.end = end
        self.index = index
        self.frame = frame
        
    def getName(self):
        return self.name
    def setName(self, name):
        self.name = name
        
    def getStart(self):
        return self.start
    def setStart(self, start):
        self.start = start
        
    def getEnd(self):
        return self.end
    def setEnd(self, end):
        self.end = end
        
    def getIndex(self):
        return self.index
    def setIndex(self, index):
        self.index = index
        
    def getFrame(self):
        return self.frame
    def setFrame(self, frame):
        self.frame = frame
###########################################################
        

#############################################
        
def createBedList():
    print("Step 1: make an arraylist of BED objects from bed file: ")
    newBed = bedLine(0, 0, 0, 0, 0)
    bedList = []

    # D:\\Bioinformatics\\TXT\\CBB520\\ver6h.BED
    # D:\\Bioinformatics\\TXT\\CBB520\\abyss.BED
    # D:\\Bioinformatics\\TXT\\CBB520\\velvet.BED
    with open('D:\\BioinformaticsCoursera\\TXT\\cbb520\\Sam2Bed\\velvet.BED', 'r') as genesReader:

        #read bed-data line by line, split with '\t';
        lines = genesReader.read().splitlines()
        print (" " + lines[0] + "\n total bed_lines: " + str(len(lines)))

    # Format of BED document;    
    # NODE_1_length_32580_cov_24.959913 2095 2196 D5ZTB5M1:245:D0GT6ACXX:8:1101:17445:3553/2 60 +
    # NODE_1_length_32580_cov_24.959913 2164 2265 D5ZTB5M1:245:D0GT6ACXX:8:1101:17445:3553/1 60 -
        for n in range(0, len(lines)-1, 1):
            values = lines[n].split('\t')
            
            currName = values[0]
            currStart = values[1]
            currEnd = values[2]
            Index = values[3]
            size = len(Index)
            currIndex = Index[0:size-2]
            # this makes one bedLine object;
            
            #1 for reads from yjm993_R1.fastq and 2 for yjm993_R2.fastq;
            #also, + means R1, - means R2;
            if (values[5] == '+'):
                currFrame = 1
            else:
                currFrame = 2

            ## set Bed object's name,start end index and frame;
            newBed.setName(currName)
            newBed.setStart(currStart)
            newBed.setEnd(currEnd)
            newBed.setIndex(currIndex)
            newBed.setFrame(currFrame)

            ## add this bed object to the bedList arrayList
            bedList.append(newBed)
            newBed = bedLine(0, 0, 0, 0, 0)
         
    return bedList

##############################################################################################
# a bruce-force method to find pairs that did not map to 'neighbor' region,
# which would be considered in-consistent
def findPair(bedList):
    print("Step 2: find inconsistent pair")
    bed = bedList
    # initial a counter to record number of in-consistent pairs;
    counter = 0
    listS = len(bedList)

    i = 0
    while (i < listS-1):
        curr = bed[i]
        j = 0
        while (j < listS-1):

            #get another bed object from the bed-list;
            comp = bed[j]
            currIndex = str(curr.getIndex())
            compIndex = str(comp.getIndex())
            currName = str(curr.getName())
            compName = str(comp.getName())
            
            currFrame = str(curr.getFrame())
            compFrame = str(comp.getFrame())

            ##########################
            # compare two BED objects;
            # if they map to two different contig or their distance is more than 600 basepairs away
            # they could be called in-consistent
            if(currIndex == compIndex):# and currFr != compFr and currFr == str(1)):
                diff = abs(int(comp.getEnd()) - int(curr.getEnd()))
                currS = int(curr.getStart())
                compS = int(comp.getStart())
                currN = str(curr.getName())
                compN = str(comp.getName())
                
                # compare name, diff, and frame;
                if(currName != compName):
                    #print("------------------1------------------------")
                    counter = counter + 1
                    j = j+1
                elif (diff >= 600):
                    #print("------------------2------------------------")
                    counter = counter + 1
                    j = j + 1
                else:
                    j = j+1
            else:
                j = j+1
        i = i+1

    return counter

################################################################
# main()
def main():
    bedList = createBedList()
    counter = findPair(bedList)
    print("final count of inconsistencies: " + str(counter))

if __name__ == "__main__":
    main()

################################################################
################################################################
#################### END #######################################
################################################################
################################################################
        
