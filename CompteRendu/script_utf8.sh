#!/bin/bash

for f in *.tex; do
    new_contenu=$(iconv --verbose -f utf-8 -t utf-8 $f);
    echo "$new_contenu" > $f.new;
done;
