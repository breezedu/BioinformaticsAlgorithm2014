import subprocess as sp
import os, sys
 
# call samtools to run in Python
# command line format: samtools view -bS -o filename.bam filename.sam
# where the .bam file is a new file in the compressed BAM format, and the .sam file
# is the file we just made above
 
 
program = 'samtools'
 
# the directory to store all sam files:
# /home/gd44/cbb520/SamFiles/
samdir = '/home/bitnami/cbb520/hw2/SamFiles/'
samfiles = os.listdir('/home/bitnami/cbb520/hw2/SamFiles/')
print (samfiles)
 
# the directory to store bam files:
# /home/gd44/cbb520/BamFiles/

bamdir = '/home/bitnami/cbb520/hw2/BamFiles/'
try:
    os.mkdir(bamdir)
except Exception as error:
    print (error)
    print ("WARNING:  BAMDIR already exists!")
 
 
 
for samfile in samfiles:
    # set the sam and bam file names
    # print(bamdir)
    # print samfile
    bamfile = bamdir + samfile.split('/')[-1].replace('.sam', '.bam')
    samfile = samdir + samfile
    print samfile
    print bamfile
    proc=sp.Popen( [program, 'view', '-Shu', '-o', bamfile, samfile] )

print("SAM to BAM ready!!!!!")
#####################
#bam to sorted bam

 
