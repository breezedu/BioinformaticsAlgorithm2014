import subprocess as sp

program = 'bowtie'
refgenomename = 'mtbH37Rc'
redfn = 'SRR006916_sample.fastq'

proc = sp.Popen( [program, refergenomename, readfn, '--refout'])
