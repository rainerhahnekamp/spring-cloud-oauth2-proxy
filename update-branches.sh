set -e

git checkout master

declare -a branches=(
  master
  solution-1-1-basics solution-1-2-advanced
  solution-2-1-basics solution-2-2-advanced
  solution-3-1-basics solution-3-2-openapi
  solution-3-3-upload-download-1-basic solution-3-3-upload-download-2-download solution-3-3-upload-download-3-misc
  solution-4-data-1-h2 solution-4-data-2-entity
)

previous=
current=

for branch in ${branches[*]}; do
  previous=$current
  current=$next
  next=$branch

  if [ ! $current = "" ]
  then
    git checkout $current
    git pull
    git tag -d `git tag | grep -E '.'`
    git merge $previous -m merge
  fi;
done

git checkout $branch
git merge $current $branch -m merge

git checkout master
