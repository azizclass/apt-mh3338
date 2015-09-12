# No need to process files and manipulate strings - we will
# pass in lists (of equal length) that correspond to
# sites views. The first list is the site visited, the second is
# the user who visited the site.

# See the test cases for more details.


def affinity(site_pair, user_sites):
    return len(filter(lambda u: site_pair[0] in u and
                      site_pair[1] in u, user_sites))


def highest_affinity(site_list, user_list, time_list):
    sites = sorted(list(set(site_list)))
    users = sorted(list(set(user_list)))
    # generate list of the list of websites each user visited
    user_sites = [[site_list[i] for i in range(len(user_list))
                  if user == user_list[i]] for user in users]
    # generate all valid site pairs
    site_pairs = [(sites[i], sites[j]) for i in range(len(sites))
                  for j in range(i + 1, len(sites))]
    # calculate affinities for each website pair
    affinities = dict((site_pair, affinity(site_pair, user_sites))
                      for site_pair in site_pairs)
    return max(affinities.iterkeys(), key=lambda key: affinities[key])
