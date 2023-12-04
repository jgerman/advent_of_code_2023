# advent_of_code_2023

## Day 1

Ten minute task, Small trick in task two that was easily solved with additional
replacements built manually. Might be fun to build them dynamically based on the
base set of the words for single digits but not really worth it.

Updated: I realized after I committed and went about my day that my solution
wasn't general enough. I handled the overlaps in my solution set, but wanted to
modify it to handle all overlaps.


## Day 2

Every year (I think) I use instaparse because it feels cleaner than splits and
regexes. Every year I have to re-learn the bits I've forgotten, having a parser
library is still a great tool in the toolbox.

## Day 3

I'm not happy with how this shook out. I had the parser quickly and it was the
right path but a foolish choice in how to store the number locations put a
subtle bug in my code... It's always tough doing the Sunday tasks because I have
football on in the background.

Rather than contort the current code to get the second star I think I'll come
back to this later and rewrite the whole thing.

## Day 4

There's probably way more elegant ways to do this but I always default ot
loop/recur because it's easy for me to think through.
