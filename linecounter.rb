require 'find'

len = ARGV.length
puts len
if ARGV.length == 2
  puts "Argument(s) missing"
  puts "Usage: ruby linecounter.rb /path/to/project .foo\n"
  exit
end

files = Hash.new
sum = 0;
Find.find(ARGV[0]) do |path|
  if File.extname(path) == ARGV[1]
  	files[File.basename(path).to_s] = File.foreach(path).inject(0) {|c, line| c+1} 
  end
end
total = 0
files.sort{|a,b| b[1]<=>a[1]}.each { |file|
  total += file[1]
  puts "#{file[1]}. #{file[0]}"
}
puts "\n"
puts "Total lines: #{total}"



